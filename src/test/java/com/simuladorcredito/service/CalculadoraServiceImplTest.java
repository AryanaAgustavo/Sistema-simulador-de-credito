package com.simuladorcredito.service;

import com.simuladorcredito.properties.TaxaJurosPorFaixaEtaria;
import com.simuladorcredito.properties.TaxaJurosSimulacaoProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculadoraServiceImplTest {

    @Mock
    private TaxaJurosSimulacaoProperties taxaJurosSimulacaoProperties;

    @InjectMocks
    private CalculadoraServiceImpl target;

    private List<TaxaJurosPorFaixaEtaria> faixasEtarias;

    @BeforeEach
    void setUp() {
        faixasEtarias = List.of(
                new TaxaJurosPorFaixaEtaria(18, 25, BigDecimal.valueOf(0.05)),
                new TaxaJurosPorFaixaEtaria(26, 40, BigDecimal.valueOf(0.03)),
                new TaxaJurosPorFaixaEtaria(41, 60, BigDecimal.valueOf(0.02)),
                new TaxaJurosPorFaixaEtaria(61, 150, BigDecimal.valueOf(0.04))
        );
    }

    @Test
    @DisplayName("Deve calcular o valor da parcela corretamente para uma taxa de 5%")
    void deveCalcularValorDaParcela() {
        BigDecimal valorEmprestimo = BigDecimal.valueOf(10000.00);
        Integer prazo = 60;
        BigDecimal taxaJuros = BigDecimal.valueOf(0.05);
        BigDecimal parcelaEsperada = BigDecimal.valueOf(188.71);
        BigDecimal parcelaCalculada = target.calcularParcelas(valorEmprestimo, prazo, taxaJuros);
        assertEquals(0, parcelaEsperada.compareTo(parcelaCalculada));
    }

    @ParameterizedTest(name = "Deve obter a taxa de juros correta para a idade {0}")
    @CsvSource({
            "25, 0.05",
            "26, 0.03",
            "46, 0.02",
            "61, 0.04"
    })
    void deveObterTaxaJurosPorIdade(Integer idade, BigDecimal taxaEsperada) {
        when(taxaJurosSimulacaoProperties.getTaxaJurosPorFaixaEtaria()).thenReturn(faixasEtarias);

        LocalDate dataNascimento = LocalDate.now().minusYears(idade);
        BigDecimal taxaJurosObtida = target.obterTaxaJurosPorIdade(dataNascimento);
        assertEquals(taxaEsperada, taxaJurosObtida);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a idade do cliente nao se enquadra em nenhuma faixa")
    void deveLancarExcecaoQuandoIdadeNaoSeEncaixa() {
        when(taxaJurosSimulacaoProperties.getTaxaJurosPorFaixaEtaria()).thenReturn(faixasEtarias);

        LocalDate dataNascimento = LocalDate.now().minusYears(10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                target.obterTaxaJurosPorIdade(dataNascimento)
        );
        assertEquals("Erro ao consultar taxa de juros por idade", exception.getMessage());
    }

    @Test
    @DisplayName("Deve calcular o valor total do emprestimo com juros")
    void deveCalcularValorTotalComJuros() {
        BigDecimal valorParcela = BigDecimal.valueOf(150.50);
        Integer prazo = 24;
        BigDecimal valorTotalEsperado = BigDecimal.valueOf(3612.00);
        BigDecimal valorTotalCalculado = target.calcularValorTotalComJuros(valorParcela, prazo);
        assertEquals(0, valorTotalEsperado.compareTo(valorTotalCalculado));
    }
}