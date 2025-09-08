package com.simuladorcredito.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "simulacao")
@Component
public class TaxaJurosSimulacaoProperties {

    private List<TaxaJurosPorFaixaEtaria> taxaJurosPorFaixaEtaria;

    public List<TaxaJurosPorFaixaEtaria> getTaxaJurosPorFaixaEtaria() {
        return taxaJurosPorFaixaEtaria;
    }

    public void setTaxaJurosPorFaixaEtaria(List<TaxaJurosPorFaixaEtaria> taxaJurosPorFaixaEtaria) {
        this.taxaJurosPorFaixaEtaria = taxaJurosPorFaixaEtaria;
    }
}
