package com.deliverytech.delivery.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de produto")
public class ProdutoDTO {

    @Schema(description = "Nome do produto", example = "Pizza Calabresa", required = true)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @Schema(description = "Preço do produto", example = "29.90", required = true)
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @DecimalMax(value = "500.00", message = "Preço não pode exceder R$ 500,00")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Pizza", required = true)
    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @Schema(description = "Descrição do produto", example = "Pizza calabresa com borda recheada")
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    private String descricao;

    @Schema(description = "ID do restaurante ao qual o produto pertence", example = "1", required = true)
    @NotNull(message = "Restaurante ID é obrigatório")
    @Positive(message = "Restaurante ID deve ser positivo")
    private Long restauranteId;

    @Schema(description = "URL da imagem do produto", example = "https://exemplo.com/pizza.jpg")
    @Pattern(
        regexp = "^(https?://).+\\.(jpg|jpeg|png|gif)$",
        message = "URL da imagem deve ser válida e ter formato JPG, JPEG, PNG ou GIF"
    )
    private String imagemUrl;

    @Schema(description = "Disponibilidade do produto", example = "true")
    @AssertTrue(message = "Produto deve estar disponível por padrão")
    private Boolean disponivel = true;

    // Construtor padrão
    public ProdutoDTO() {}

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
