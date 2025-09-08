package com.simuladorcredito.service;

import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NotificacaoSimulacaoService extends NotificacaoServiceImpl {

    @Autowired
    public NotificacaoSimulacaoService(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void enviarNotificacaoSimulacao(PedidoSimulacaoBusiness pedidoSimulacaoBusiness) {
        String email = pedidoSimulacaoBusiness.getCliente().getEmail();
        String assuntoEmail = "Simulação de Empréstimo Concluída";
        String conteudoEmail = montarConteudoEmail(pedidoSimulacaoBusiness);
        notificarClientePorEmail(email, assuntoEmail, conteudoEmail);
    }

    private String montarConteudoEmail(PedidoSimulacaoBusiness pedidoSimulacaoBusiness) {
        return String.format(
                "<h1>Olá!</h1>" +
                        "<p>Sua simulação de empréstimo foi concluída, confira:</p> " +
                        "<h2>Valor do Empréstimo: R$ %.2f </h2> " +
                        "<p>com uma taxa de juros de %.2f%% </p>" +
                        "<p>e parcelas de R$%.2f.</p>",
                pedidoSimulacaoBusiness.getSolicitacaoSimulacao().getValorTotalEmprestimo(),
                pedidoSimulacaoBusiness.getSolicitacaoSimulacao().getTaxaJuros().multiply(BigDecimal.valueOf(100)),
                pedidoSimulacaoBusiness.getSolicitacaoSimulacao().getPagamento().getParcela()
                );
    }

}
