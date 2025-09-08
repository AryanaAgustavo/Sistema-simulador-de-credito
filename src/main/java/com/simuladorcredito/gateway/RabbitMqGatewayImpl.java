package com.simuladorcredito.gateway;

import com.simuladorcredito.entrypoint.listener.NotificarSimulacaoClienteListener;
import com.simuladorcredito.entrypoint.listener.ProcessarSimulacaoListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;

@Service
public class RabbitMqGatewayImpl implements RabbitMqGateway {

    /*Implementar o envio para fila Rabbit*/

    //remover após implementação do Rabbit
    private final ProcessarSimulacaoListener processarSimulacaoListener;
    private final NotificarSimulacaoClienteListener notificarSimulacaoClienteListener;

    @Autowired
    public RabbitMqGatewayImpl(ProcessarSimulacaoListener processarSimulacaoListener,
                               NotificarSimulacaoClienteListener notificarSimulacaoClienteListener) {
        this.processarSimulacaoListener = processarSimulacaoListener;
        this.notificarSimulacaoClienteListener = notificarSimulacaoClienteListener;
    }

    @Override
    public void enviarParaProcessarSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness) {
//        processarSimulacaoListener.processarSimulacao(pedidoSimulacaoBusiness);
    }

    @Override
    public void enviarParaNotificacao(PedidoSimulacaoBusiness simulacao) {

    }
}
