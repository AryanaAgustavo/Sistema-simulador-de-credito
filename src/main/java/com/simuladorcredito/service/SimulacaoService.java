package com.simuladorcredito.service;

import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;

import java.util.List;

public interface SimulacaoService {

    void simularEmLote(List<PedidoSimulacaoBusiness> pedidoSimulacaoList);

    void processarSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness);
}
