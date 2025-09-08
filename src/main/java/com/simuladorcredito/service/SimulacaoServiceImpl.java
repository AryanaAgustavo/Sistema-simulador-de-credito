package com.simuladorcredito.service;

import com.simuladorcredito.entrypoint.listener.NotificarSimulacaoClienteListener;
import com.simuladorcredito.gateway.RabbitMqGateway;
import com.simuladorcredito.gateway.repository.SimulacaoRepository;
import com.simuladorcredito.gateway.repository.data.SimulacaoEntity;
import com.simuladorcredito.mapper.SimulacaoMapper;
import com.simuladorcredito.service.data.ErroBusiness;
import com.simuladorcredito.service.data.PagamentoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class SimulacaoServiceImpl implements SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final CalculadoraService calculadoraService;
    private final SimulacaoMapper simulacaoMapper;
    private final RabbitMqGateway rabbitMqGateway;
    private final NotificarSimulacaoClienteListener notificarSimulacaoClienteListener; //remover após implementação do Rabbit

    private static final Logger LOGGER = Logger.getLogger(SimulacaoServiceImpl.class.getName());

    @Autowired
    public SimulacaoServiceImpl(SimulacaoRepository simulacaoRepository, CalculadoraService calculadoraService,
                                SimulacaoMapper simulacaoMapper,
                                RabbitMqGateway rabbitMqGateway, NotificarSimulacaoClienteListener notificarSimulacaoClienteListener) {
        this.simulacaoRepository = simulacaoRepository;
        this.calculadoraService = calculadoraService;
        this.simulacaoMapper = simulacaoMapper;
        this.rabbitMqGateway = rabbitMqGateway;
        this.notificarSimulacaoClienteListener = notificarSimulacaoClienteListener;
    }

    @Override
    public void simularEmLote(List<PedidoSimulacaoBusiness> pedidoSimulacaoList) {
//        pedidoSimulacaoList.forEach(rabbitMqGateway::enviarParaProcessarSimulacao); //descomentar após implementação do Rabbit
        pedidoSimulacaoList.forEach(this::processarSimulacao);

    }

    @Override
    public void processarSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness) {
        LOGGER.info("Iniciando processamento da simulacao: " + pedidoSimulacaoBusiness);
        PedidoSimulacaoBusiness simulacaoValidada = validarPedidoSimulacao(pedidoSimulacaoBusiness);

        if (nonNull(simulacaoValidada.getErros()) && !simulacaoValidada.getErros().isEmpty()) {
            gravarSimulacao(simulacaoValidada);
            return;
        }
        PedidoSimulacaoBusiness simulacaoSalva = simulacaoMapper.entityToBusiness(gravarSimulacao(pedidoSimulacaoBusiness));
        LOGGER.info("Enviando para o calculo de taxas e parcelas: " + simulacaoSalva.getId());
        BigDecimal taxaJuros = calculadoraService.obterTaxaJurosPorIdade(pedidoSimulacaoBusiness.getCliente().getDataNascimento());
        BigDecimal valorParcelas = calculadoraService.calcularParcelas(simulacaoSalva.getSolicitacaoSimulacao().getValorTotalEmprestimo(),
                                                                                            simulacaoSalva.getSolicitacaoSimulacao().getPrazo(), taxaJuros);
        BigDecimal valorTotalComJuros = calculadoraService.calcularValorTotalComJuros(valorParcelas, simulacaoSalva.getSolicitacaoSimulacao().getPrazo());
        atualizarSimulacao(simulacaoSalva, taxaJuros, valorParcelas, valorTotalComJuros);
        enviarParaNotificacao(simulacaoSalva);
    }

    private PedidoSimulacaoBusiness validarPedidoSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness) {
        LOGGER.info("Validando simulacao");

        List<ErroBusiness> erros = new ArrayList<>();
        if (isNull(pedidoSimulacaoBusiness.getSolicitacaoSimulacao())
                        || isNull(pedidoSimulacaoBusiness.getSolicitacaoSimulacao().getValorTotalEmprestimo())
                            || isNull(pedidoSimulacaoBusiness.getSolicitacaoSimulacao().getPrazo())) {
            erros.add(new ErroBusiness("SIM_0001", "Dados da solicitação de simulação não preenchidos"));
        }

        if (isNull(pedidoSimulacaoBusiness.getCliente())
                || isNull(pedidoSimulacaoBusiness.getCliente().getDataNascimento())
                    || isNull(pedidoSimulacaoBusiness.getCliente().getCpf())
                        || isNull(pedidoSimulacaoBusiness.getCliente().getEmail())) {
            erros.add(new ErroBusiness("SIM_0001", "Dados do cliente não preenchidos"));
        }
        if (!erros.isEmpty()) {
            LOGGER.info("Erros encontrados durante a validacao: " + erros);
            pedidoSimulacaoBusiness.setErros(erros);
        }
        return pedidoSimulacaoBusiness;
    }

    private SimulacaoEntity gravarSimulacao(PedidoSimulacaoBusiness pedidoSimulacao) {
        pedidoSimulacao.setDataCriacao(LocalDateTime.now());
        return simulacaoRepository.save(simulacaoMapper.businessToEntity(pedidoSimulacao));
    }

    private void atualizarSimulacao(PedidoSimulacaoBusiness pedidoSimulacao, BigDecimal taxaJuros, BigDecimal valorParcelas, BigDecimal valorTotalComJuros) {
        pedidoSimulacao.setDataAtualizacao(LocalDateTime.now());
        pedidoSimulacao.getSolicitacaoSimulacao().setTaxaJuros(taxaJuros);
        PagamentoBusiness pagamento = new PagamentoBusiness();
        pagamento.setParcela(valorParcelas);
        pagamento.setTotal(valorTotalComJuros);
        pedidoSimulacao.getSolicitacaoSimulacao().setPagamento(pagamento);
        simulacaoRepository.save(simulacaoMapper.businessToEntity(pedidoSimulacao));
    }

    private void enviarParaNotificacao(PedidoSimulacaoBusiness simulacaoSalva) {
        //rabbitMqGateway.enviarParaNotificacao(simulacaoSalva); //descomentar após implementação do Rabbit
        LOGGER.info("Enviando simulacao para notificar ao cliente");
        notificarSimulacaoClienteListener.notificarClienteSimulacao(simulacaoSalva.getId());
    }
}
