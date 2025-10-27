package com.deliverytech.delivery.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String categoria;
    private String telefone;
    private BigDecimal taxaEntrega;
    private Boolean ativo;

    // Novos campos usados pelos testes
    private Integer tempoEntrega;
    private String horarioFuncionamento;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public BigDecimal getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    // Novos getters/setters
    public Integer getTempoEntrega() { return tempoEntrega; }
    public void setTempoEntrega(Integer tempoEntrega) { this.tempoEntrega = tempoEntrega; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }

    // Conveniência para evitar NPE
    public boolean isAtivo() { return Boolean.TRUE.equals(ativo); }
}
