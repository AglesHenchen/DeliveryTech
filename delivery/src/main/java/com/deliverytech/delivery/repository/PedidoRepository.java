package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    // Pedidos por restaurante e status
    List<Pedido> findByRestauranteIdAndStatus(Long restauranteId, StatusPedido status);

    // Pedidos por status e período (com paginação)
    Page<Pedido> findByStatusAndDataPedidoBetween(StatusPedido status, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

}
