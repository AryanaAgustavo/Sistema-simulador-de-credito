package com.simuladorcredito.service;

import com.simuladorcredito.service.data.ClienteBusiness;
import com.simuladorcredito.service.data.PagamentoBusiness;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;
import com.simuladorcredito.service.data.SolicitacaoSimulacaoBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificacaoSimulacaoServiceTest {

    @Spy
    @InjectMocks
    private NotificacaoSimulacaoService target;

    @Mock
    private JavaMailSender mailSender;

    private PedidoSimulacaoBusiness pedidoSimulacaoBusiness;

    @BeforeEach
    void setUp() {
        ClienteBusiness cliente = new ClienteBusiness();
        cliente.setEmail("teste@exemplo.com");

        PagamentoBusiness pagamento = new PagamentoBusiness();
        pagamento.setParcela(BigDecimal.valueOf(900));

        SolicitacaoSimulacaoBusiness solicitacao = new SolicitacaoSimulacaoBusiness();
        solicitacao.setValorTotalEmprestimo(BigDecimal.valueOf(10000));
        solicitacao.setTaxaJuros(BigDecimal.valueOf(0.05));
        solicitacao.setPagamento(pagamento);

        pedidoSimulacaoBusiness = new PedidoSimulacaoBusiness();
        pedidoSimulacaoBusiness.setCliente(cliente);
        pedidoSimulacaoBusiness.setSolicitacaoSimulacao(solicitacao);
    }

    @Test
    @DisplayName("Deve enviar a notificação de simulação por e-mail")
    void deveEnviarNotificacaoSimulacao() {
        target.enviarNotificacaoSimulacao(pedidoSimulacaoBusiness);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> assuntoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> conteudoCaptor = ArgumentCaptor.forClass(String.class);

        verify(target).notificarClientePorEmail(emailCaptor.capture(), assuntoCaptor.capture(), conteudoCaptor.capture());
        assertEquals("teste@exemplo.com", emailCaptor.getValue());
        assertEquals("Simulação de Empréstimo Concluída", assuntoCaptor.getValue());
        String conteudoCapturado = conteudoCaptor.getValue();
        assertEquals("<h1>Olá!</h1><p>Sua simulação de empréstimo foi concluída, confira:</p> <h2>Valor do Empréstimo: R$ 10000,00 </h2> <p>com uma taxa de juros de 5,00% </p><p>e parcelas de R$900,00.</p>", conteudoCapturado);
    }
}