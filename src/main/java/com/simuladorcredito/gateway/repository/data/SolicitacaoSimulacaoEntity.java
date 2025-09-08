package com.simuladorcredito.gateway.repository.data;

import java.math.BigDecimal;

public class SolicitacaoSimulacaoEntity {

    private BigDecimal valorTotalEmprestimo;
    private Integer prazo;
    private BigDecimal taxaJuros;
    private PagamentoEntity pagamento;

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

    public PagamentoEntity getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoEntity pagamento) {
        this.pagamento = pagamento;
    }

}
