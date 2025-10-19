package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    // Buscar por categoria
    List<Restaurante> findByCategoria(String categoria);

    // Buscar por nome
    List<Restaurante> findByNome(String nome);

    // Buscar restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Por taxa de entrega menor ou igual
    List<Restaurante> findByTaxaEntregaLessThanEqual(BigDecimal taxa);

    // Top 5 restaurantes por nome (ordem alfabética)
    List<Restaurante> findTop5ByOrderByNomeAsc();
}
