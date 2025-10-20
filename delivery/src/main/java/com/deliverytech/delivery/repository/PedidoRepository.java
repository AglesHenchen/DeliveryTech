package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Pedidos por cliente
    List<Pedido> findByClienteId(Long clienteId);

    // 10 pedidos mais recentes
    List<Pedido> findTop10ByOrderByDataPedidoDesc();

    // Pedidos por período
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    // Total de vendas por restaurante
    @Query("SELECT p.restaurante.nome, SUM(p.valorTotal) " +
           "FROM Pedido p " +
           "GROUP BY p.restaurante.id, p.restaurante.nome " +
           "ORDER BY SUM(p.valorTotal) DESC")
    List<Object[]> calcularTotalVendasPorRestaurante();

    // Pedidos com valor acima de um determinado valor
    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > :valor ORDER BY p.valorTotal DESC")
    List<Pedido> buscarPedidosComValorAcimaDe(@Param("valor") BigDecimal valor);

    // Relatório de pedidos por período
    @Query("SELECT p FROM Pedido p " +
           "WHERE p.dataPedido BETWEEN :inicio AND :fim " +
           "ORDER BY p.dataPedido DESC")
    List<Pedido> relatorioPedidosPorPeriodoEStatus(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);
}
