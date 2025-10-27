package com.deliverytech.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CalculoPedidoDTO {

    @NotEmpty(message = "O pedido deve conter ao menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    // Getters e Setters
    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
