package com.simuladorcredito.service;

import com.simuladorcredito.entrypoint.listener.NotificarSimulacaoClienteListener;
import com.simuladorcredito.gateway.RabbitMqGateway;
import com.simuladorcredito.gateway.repository.SimulacaoRepository;
import com.simuladorcredito.gateway.repository.data.SimulacaoEntity;
import com.simuladorcredito.mapper.SimulacaoMapper;
import com.simuladorcredito.mapper.SimulacaoMapperImpl;
import com.simuladorcredito.service.data.ClienteBusiness;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;
import com.simuladorcredito.service.data.SolicitacaoSimulacaoBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacaoServiceImplTest {

    @InjectMocks
    private SimulacaoServiceImpl target;

    @Mock
    private SimulacaoRepository simulacaoRepository;
    @Mock
    private CalculadoraService calculadoraService;
    @Mock
    private RabbitMqGateway rabbitMqGateway;
    @Mock
    private NotificarSimulacaoClienteListener notificarSimulacaoClienteListener;
    @Spy
    private SimulacaoMapper simulacaoMapper = new SimulacaoMapperImpl();

    private PedidoSimulacaoBusiness pedidoSimulacaoBusiness;
    private SimulacaoEntity simulacaoEntity;
    private LocalDate dataNascimento;

    @BeforeEach
    void setUp() {
        dataNascimento = LocalDate.of(1995, 5, 10);

        ClienteBusiness cliente = new ClienteBusiness();
        cliente.setDataNascimento(dataNascimento);
        cliente.setCpf("12345678900");
        cliente.setEmail("email@.com");

        SolicitacaoSimulacaoBusiness solicitacao = new SolicitacaoSimulacaoBusiness();
        solicitacao.setValorTotalEmprestimo(BigDecimal.valueOf(10000));
        solicitacao.setPrazo(12);

        pedidoSimulacaoBusiness = new PedidoSimulacaoBusiness();
        pedidoSimulacaoBusiness.setId("123");
        pedidoSimulacaoBusiness.setCliente(cliente);
        pedidoSimulacaoBusiness.setSolicitacaoSimulacao(solicitacao);

        simulacaoEntity = new SimulacaoEntity();
        simulacaoEntity.setId("123");
    }

    @Test
    @DisplayName("Deve processar a simulação com sucesso e atualizar os dados")
    void deveProcessarSimulacaoEAtualizarDados() {
        when(simulacaoRepository.save(any(SimulacaoEntity.class))).thenReturn(simulacaoEntity);

        when(simulacaoMapper.businessToEntity(any(PedidoSimulacaoBusiness.class))).thenReturn(simulacaoEntity);
        when(simulacaoMapper.entityToBusiness(any(SimulacaoEntity.class))).thenReturn(pedidoSimulacaoBusiness);
        when(calculadoraService.obterTaxaJurosPorIdade(any(LocalDate.class))).thenReturn(BigDecimal.valueOf(0.05));
        when(calculadoraService.calcularParcelas(any(BigDecimal.class), any(Integer.class), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(900));
        when(calculadoraService.calcularValorTotalComJuros(any(BigDecimal.class), any(Integer.class))).thenReturn(BigDecimal.valueOf(10800));

        target.processarSimulacao(pedidoSimulacaoBusiness);

        verify(simulacaoRepository, times(2)).save(any(SimulacaoEntity.class));
        verify(calculadoraService).obterTaxaJurosPorIdade(dataNascimento);
        verify(calculadoraService).calcularParcelas(BigDecimal.valueOf(10000), 12, BigDecimal.valueOf(0.05));
        verify(calculadoraService).calcularValorTotalComJuros(BigDecimal.valueOf(900), 12);
        verify(notificarSimulacaoClienteListener, times(1)).notificarClienteSimulacao(pedidoSimulacaoBusiness.getId());
    }

    @Test
    @DisplayName("Deve adicionar erro quando dados da solicitacao estiverem ausentes")
    void deveAdicionarErroQuandoSolicitacaoAusente() {
        pedidoSimulacaoBusiness.setSolicitacaoSimulacao(null);
        target.processarSimulacao(pedidoSimulacaoBusiness);

        assertNotNull(pedidoSimulacaoBusiness.getErros());
        assertEquals(1, pedidoSimulacaoBusiness.getErros().size());
        assertEquals("SIM_0001", pedidoSimulacaoBusiness.getErros().get(0).getCodigo());
        assertEquals("Dados da solicitação de simulação não preenchidos", pedidoSimulacaoBusiness.getErros().get(0).getDescricao());

        verify(simulacaoRepository,  times(1)).save(any(SimulacaoEntity.class));
        verify(calculadoraService, never()).obterTaxaJurosPorIdade(any());
        verify(notificarSimulacaoClienteListener, never()).notificarClienteSimulacao(any());
    }

    @Test
    @DisplayName("Deve adicionar erro quando dados do cliente estiverem ausentes")
    void deveAdicionarErroQuandoClienteAusente() {
        pedidoSimulacaoBusiness.setCliente(null);
        target.processarSimulacao(pedidoSimulacaoBusiness);

        assertNotNull(pedidoSimulacaoBusiness.getErros());
        assertEquals(1, pedidoSimulacaoBusiness.getErros().size());
        assertEquals("SIM_0001", pedidoSimulacaoBusiness.getErros().get(0).getCodigo());
        assertEquals("Dados do cliente não preenchidos", pedidoSimulacaoBusiness.getErros().get(0).getDescricao());

        verify(simulacaoRepository,  times(1)).save(any(SimulacaoEntity.class));
        verify(calculadoraService, never()).obterTaxaJurosPorIdade(any());
        verify(notificarSimulacaoClienteListener, never()).notificarClienteSimulacao(any());
    }

    @Test
    @DisplayName("Deve adicionar multiplos erros quando dados do cliente e solicitacao estiverem ausentes")
    void deveAdicionarMultiplosErrosQuandoDadosAusentes() {
        pedidoSimulacaoBusiness.setSolicitacaoSimulacao(null);
        pedidoSimulacaoBusiness.setCliente(null);
        target.processarSimulacao(pedidoSimulacaoBusiness);

        assertNotNull(pedidoSimulacaoBusiness.getErros());
        assertEquals(2, pedidoSimulacaoBusiness.getErros().size());
        assertEquals("Dados da solicitação de simulação não preenchidos", pedidoSimulacaoBusiness.getErros().get(0).getDescricao());
        assertEquals("Dados do cliente não preenchidos", pedidoSimulacaoBusiness.getErros().get(1).getDescricao());

        verify(simulacaoRepository, times(1)).save(any(SimulacaoEntity.class));
        verify(calculadoraService, never()).obterTaxaJurosPorIdade(any());
        verify(notificarSimulacaoClienteListener, never()).notificarClienteSimulacao(any());
    }

}