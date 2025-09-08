package com.simuladorcredito.entrypoint.listener;

import com.simuladorcredito.gateway.repository.SimulacaoRepository;
import com.simuladorcredito.mapper.SimulacaoMapper;
import com.simuladorcredito.service.NotificacaoSimulacaoService;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificarSimulacaoClienteListener {

    private final NotificacaoSimulacaoService notificacaoSimulacaoService;
    private final SimulacaoRepository simulacaoRepository;
    private final SimulacaoMapper simulacaoMapper;

    @Autowired
    public NotificarSimulacaoClienteListener(NotificacaoSimulacaoService notificacaoSimulacaoService, SimulacaoRepository simulacaoRepository, SimulacaoMapper simulacaoMapper) {
        this.notificacaoSimulacaoService = notificacaoSimulacaoService;
        this.simulacaoRepository = simulacaoRepository;
        this.simulacaoMapper = simulacaoMapper;
    }

    public void notificarClienteSimulacao(String idSimulacao) {
        PedidoSimulacaoBusiness simulacao = simulacaoMapper.entityToBusiness(simulacaoRepository.findById(idSimulacao).get());
        notificacaoSimulacaoService.enviarNotificacaoSimulacao(simulacao);
    }
}
