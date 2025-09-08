package com.simuladorcredito.gateway.repository.data;

import java.math.BigDecimal;

public class PagamentoEntity {

    private BigDecimal total;
    private BigDecimal parcela;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getParcela() {
        return parcela;
    }

    public void setParcela(BigDecimal parcela) {
        this.parcela = parcela;
    }
}
