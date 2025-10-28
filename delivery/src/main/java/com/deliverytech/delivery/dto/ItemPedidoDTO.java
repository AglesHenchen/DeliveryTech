package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.*;

public class ItemPedidoDTO {

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "Quantidade máxima é 10")
    private Integer quantidade;

    @Size(max = 200, message = "Observações não podem exceder 200 caracteres")
    private String observacoes;

    // Construtor padrão
    public ItemPedidoDTO() {
    }

    // Construtor com todos os campos
    public ItemPedidoDTO(Long produtoId, Integer quantidade, String observacoes) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
