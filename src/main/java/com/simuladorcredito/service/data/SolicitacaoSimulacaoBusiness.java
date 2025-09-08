package com.simuladorcredito.service.data;

import com.simuladorcredito.gateway.repository.data.PagamentoEntity;

import java.math.BigDecimal;

public class SolicitacaoSimulacaoBusiness {

    private BigDecimal valorTotalEmprestimo;
    private Integer prazo;
    private BigDecimal taxaJuros;
    private PagamentoBusiness pagamento;

    public SolicitacaoSimulacaoBusiness() {
    }

    public SolicitacaoSimulacaoBusiness(BigDecimal valorTotalEmprestimo, Integer prazo, BigDecimal taxaJuros, PagamentoBusiness pagamento) {
        this.valorTotalEmprestimo = valorTotalEmprestimo;
        this.prazo = prazo;
        this.taxaJuros = taxaJuros;
        this.pagamento = pagamento;
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

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public PagamentoBusiness getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoBusiness pagamento) {
        this.pagamento = pagamento;
    }

}
