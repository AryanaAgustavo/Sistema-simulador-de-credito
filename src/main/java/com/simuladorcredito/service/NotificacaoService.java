package com.simuladorcredito.service;


public interface NotificacaoService {

    void notificarClientePorEmail(String email, String assuntoEmail, String mensagem);


}
