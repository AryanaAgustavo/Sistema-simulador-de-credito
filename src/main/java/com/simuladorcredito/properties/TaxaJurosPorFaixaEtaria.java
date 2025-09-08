package com.simuladorcredito.properties;

import java.math.BigDecimal;

public class TaxaJurosPorFaixaEtaria {

    private Integer idadeMinima;
    private Integer idadeMaxima;
    private BigDecimal taxaJuros;

    public TaxaJurosPorFaixaEtaria(Integer idadeMinima, Integer idadeMaxima, BigDecimal taxaJuros) {
        this.idadeMinima = idadeMinima;
        this.idadeMaxima = idadeMaxima;
        this.taxaJuros = taxaJuros;
    }

    public Integer getIdadeMinima() {
        return idadeMinima;
    }

    public void setIdadeMinima(Integer idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public Integer getIdadeMaxima() {
        return idadeMaxima;
    }

    public void setIdadeMaxima(Integer idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
}
