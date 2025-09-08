package com.simuladorcredito.gateway;

import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;

public interface RabbitMqGateway {

    void enviarParaProcessarSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness);

    void enviarParaNotificacao(PedidoSimulacaoBusiness simulacao);
}
