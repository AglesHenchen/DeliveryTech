package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public class CalculoPedidoResponseDTO {

    private BigDecimal total;

    public CalculoPedidoResponseDTO() {}

    public CalculoPedidoResponseDTO(BigDecimal total) {
        this.total = total;
    }

    // Getter e Setter
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
