package com.simuladorcredito.service.data;

import java.time.LocalDate;

public class ClienteBusiness {

    private String cpf;
    private LocalDate dataNascimento;
    private String email;

    public ClienteBusiness() {
    }

    public ClienteBusiness(String cpf, LocalDate dataNascimento, String email) {
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
