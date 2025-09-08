package com.simuladorcredito.entrypoint.data.request;

import java.time.LocalDate;

public class ClienteRequest {

    private String cpf;
    private LocalDate dataNascimento;
    private String email;

    public ClienteRequest(String cpf, LocalDate dataNascimento, String email) {
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
