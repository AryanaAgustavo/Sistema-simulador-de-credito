package com.simuladorcredito.gateway.repository.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "simulacoes")
public class SimulacaoEntity {

    @Id
    private String id;
    private ClienteEntity cliente;
    private SolicitacaoSimulacaoEntity solicitacaoSimulacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private List<ErroEntity> erros;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public SolicitacaoSimulacaoEntity getSolicitacaoSimulacao() {
        return solicitacaoSimulacao;
    }

    public void setSolicitacaoSimulacao(SolicitacaoSimulacaoEntity solicitacaoSimulacao) {
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

    public List<ErroEntity> getErros() {
        return erros;
    }

    public void setErros(List<ErroEntity> erros) {
        this.erros = erros;
    }
}
