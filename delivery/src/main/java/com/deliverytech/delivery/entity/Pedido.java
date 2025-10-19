package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.deliverytech.delivery.enums.StatusPedido;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private String enderecoEntrega;

    private BigDecimal subtotal;

    private BigDecimal taxaEntrega;

    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    // Getters e Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Restaurante getRestaurante() { return restaurante; }

    public void setRestaurante(Restaurante restaurante) { this.restaurante = restaurante; }

    public LocalDateTime getDataPedido() { return dataPedido; }

    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public StatusPedido getStatus() { return status; }

    public void setStatus(StatusPedido status) { this.status = status; }

    public String getEnderecoEntrega() { return enderecoEntrega; }

    public void setEnderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public BigDecimal getSubtotal() { return subtotal; }

    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTaxaEntrega() { return taxaEntrega; }

    public void setTaxaEntrega(BigDecimal taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public BigDecimal getValorTotal() { return valorTotal; }

    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public List<ItemPedido> getItens() { return itens; }

    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}
