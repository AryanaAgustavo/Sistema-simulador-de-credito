package com.simuladorcredito.service.data;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoSimulacaoBusiness {

    private String id;
    private ClienteBusiness cliente;
    private SolicitacaoSimulacaoBusiness solicitacaoSimulacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<ErroBusiness> erros;

    public PedidoSimulacaoBusiness() {
    }

    public PedidoSimulacaoBusiness(String id, ClienteBusiness cliente, SolicitacaoSimulacaoBusiness solicitacaoSimulacao, LocalDateTime dataCriacao) {
        this.id = id;
        this.cliente = cliente;
        this.solicitacaoSimulacao = solicitacaoSimulacao;
        this.dataCriacao = dataCriacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClienteBusiness getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBusiness cliente) {
        this.cliente = cliente;
    }

    public SolicitacaoSimulacaoBusiness getSolicitacaoSimulacao() {
        return solicitacaoSimulacao;
    }

    public void setSolicitacaoSimulacao(SolicitacaoSimulacaoBusiness solicitacaoSimulacao) {
        this.solicitacaoSimulacao = solicitacaoSimulacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<ErroBusiness> getErros() {
        return erros;
    }

    public void setErros(List<ErroBusiness> erros) {
        this.erros = erros;
    }
}
