package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    Page<Restaurante> findByCategoriaAndAtivo(String categoria, Boolean ativo, Pageable pageable);

    Page<Restaurante> findByCategoria(String categoria, Pageable pageable);

    Page<Restaurante> findByAtivo(Boolean ativo, Pageable pageable);

    List<Restaurante> findByCategoria(String categoria);

    List<Restaurante> findByAtivo(Boolean ativo);

    // Se você implementar proximidade geográfica, adicione aqui métodos específicos ou use @Query nativo
}
