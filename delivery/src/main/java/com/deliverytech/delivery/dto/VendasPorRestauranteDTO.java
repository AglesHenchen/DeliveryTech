package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public class VendasPorRestauranteDTO {

    private Long restauranteId;
    private String nomeRestaurante;
    private BigDecimal totalVendas;

    // Getters e Setters
    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }
}
