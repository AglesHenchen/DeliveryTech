package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Buscar cliente por email
    Optional<Cliente> findByEmail(String email);

    // Buscar apenas clientes ativos
    List<Cliente> findByAtivoTrue();

    // Busca por nome (parcial, ignorando case)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Ranking dos 10 clientes com mais pedidos
    @Query(value = "SELECT c.nome, COUNT(p.id) AS total_pedidos " +
            "FROM cliente c " +
            "LEFT JOIN pedido p ON c.id = p.cliente_id " +
            "GROUP BY c.id, c.nome " +
            "ORDER BY total_pedidos DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> rankingClientesPorPedidos();
}
