package com.simuladorcredito.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalculadoraService {

    BigDecimal calcularParcelas(BigDecimal valorTotalEmprestimo, Integer prazo, BigDecimal taxaJurosTotal);

    BigDecimal obterTaxaJurosPorIdade(LocalDate dataNascimentoCliente);

    BigDecimal calcularValorTotalComJuros(BigDecimal valorParcela, Integer prazo);
}