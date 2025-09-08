package com.simuladorcredito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simuladorcredito.properties.TaxaJurosPorFaixaEtaria;
import com.simuladorcredito.properties.TaxaJurosSimulacaoProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.logging.Logger;

@Service
public class CalculadoraServiceImpl implements CalculadoraService {

    private TaxaJurosSimulacaoProperties taxaJurosSimulacaoProperties;

    private static final Logger LOGGER = Logger.getLogger(CalculadoraServiceImpl.class.getName());

    @Autowired
    public CalculadoraServiceImpl(TaxaJurosSimulacaoProperties taxaJurosSimulacaoProperties) {
        this.taxaJurosSimulacaoProperties = taxaJurosSimulacaoProperties;
    }

    @Override
    public BigDecimal calcularParcelas(BigDecimal valorTotalEmprestimo, Integer prazo, BigDecimal taxaJurosTotal) {
        LOGGER.info("Calculando Parcelas");
        BigDecimal taxaJurosMensal = taxaJurosTotal.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaJurosMensal);
        BigDecimal umMaisTaxaElevadoAoPrazo = BigDecimal.ONE.divide(umMaisTaxa.pow(prazo), 10, RoundingMode.HALF_UP);

        BigDecimal denominador = BigDecimal.ONE.subtract(umMaisTaxaElevadoAoPrazo);
        BigDecimal numerador = valorTotalEmprestimo.multiply(taxaJurosMensal);

        return numerador.divide(denominador, 2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal obterTaxaJurosPorIdade(LocalDate dataNascimentoCliente) {
        LOGGER.info("Calculando taxa de juros");
        Integer idade = java.time.Period.between(dataNascimentoCliente, LocalDate.now()).getYears();
        return taxaJurosSimulacaoProperties.getTaxaJurosPorFaixaEtaria().stream()
                .filter(faixaEtaria ->
                        faixaEtaria.getIdadeMinima().compareTo(idade) <= 0
                                && faixaEtaria.getIdadeMaxima().compareTo(idade) >= 0
                )
                .findFirst()
                .map(TaxaJurosPorFaixaEtaria::getTaxaJuros)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao consultar taxa de juros por idade"));
    }

    @Override
    public BigDecimal calcularValorTotalComJuros(BigDecimal valorParcela, Integer prazo) {
        LOGGER.info("Calculando Valor Total com juros");
        BigDecimal valorTotal = valorParcela.multiply(BigDecimal.valueOf(prazo));
        return valorTotal.setScale(2, RoundingMode.HALF_UP);
    }
}
