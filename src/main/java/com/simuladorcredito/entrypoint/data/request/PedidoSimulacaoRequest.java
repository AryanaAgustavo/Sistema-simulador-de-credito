package com.simuladorcredito.entrypoint.data.request;

import java.math.BigDecimal;

public class PedidoSimulacaoRequest {

    private ClienteRequest cliente;
    private BigDecimal valorTotalEmprestimo;
    private Integer prazo;

    public PedidoSimulacaoRequest(ClienteRequest cliente, BigDecimal valorTotalEmprestimo, Integer prazo) {
        this.cliente = cliente;
        this.valorTotalEmprestimo = valorTotalEmprestimo;
        this.prazo = prazo;
    }

    public ClienteRequest getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRequest cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotalEmprestimo() {
        return valorTotalEmprestimo;
    }

    public void setValorTotalEmprestimo(BigDecimal valorTotalEmprestimo) {
        this.valorTotalEmprestimo = valorTotalEmprestimo;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }
}
