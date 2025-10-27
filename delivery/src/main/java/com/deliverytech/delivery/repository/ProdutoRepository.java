package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Produtos por restaurante
    List<Produto> findByRestauranteId(Long restauranteId);

    // Produtos por categoria
    List<Produto> findByCategoria(String categoria);

    // Busca por nome (contendo, case-insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Por faixa de preço (menor ou igual)
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);

    // Top 5 produtos mais vendidos
    @Query(value = "SELECT p.nome, COUNT(ip.produto_id) AS quantidade_vendida " +
            "FROM produto p " +
            "LEFT JOIN item_pedido ip ON p.id = ip.produto_id " +
            "GROUP BY p.id, p.nome " +
            "ORDER BY quantidade_vendida DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> produtosMaisVendidos();
}
