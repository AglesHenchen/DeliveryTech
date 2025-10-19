package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class StatusPedidoDTO {

    @NotBlank(message = "Status é obrigatório")
    @Pattern(regexp = "^(PENDENTE|EM_PREPARACAO|ENTREGUE|CANCELADO)$", message = "Status inválido")
    private String status;

    // Getter e Setter

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
