package com.deliverytech.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import com.deliverytech.delivery.validation.ValidCEP;

public class PedidoDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoEntrega;

    @NotBlank(message = "CEP é obrigatório")
    @ValidCEP
    private String cep;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    @Pattern(
        regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$",
        message = "Forma de pagamento deve ser: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO ou PIX"
    )
    private String formaPagamento;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
