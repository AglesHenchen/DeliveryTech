package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.exception.EntityNotFoundException;
import com.deliverytech.delivery.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@Validated
public class RestauranteController {

    private final List<RestauranteDTO> restaurantes = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listarTodos() {
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscarPorId(
            @PathVariable @Positive(message = "ID deve ser positivo") Long id) {

        if (id <= 0 || id > restaurantes.size()) {
            throw new EntityNotFoundException("Restaurante", id);
        }

        // Simulação de busca pelo ID (0-based)
        RestauranteDTO restaurante = restaurantes.get(id.intValue() - 1);
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> criar(@Valid @RequestBody RestauranteDTO restauranteDTO) {

        // Verificar se já existe restaurante com mesmo nome
        boolean nomeExiste = restaurantes.stream()
                .anyMatch(r -> r.getNome().equalsIgnoreCase(restauranteDTO.getNome()));

        if (nomeExiste) {
            throw new ConflictException(
                    "Já existe um restaurante com este nome",
                    "nome",
                    restauranteDTO.getNome()
            );
        }

        restaurantes.add(restauranteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> atualizar(
            @PathVariable @Positive(message = "ID deve ser positivo") Long id,
            @Valid @RequestBody RestauranteDTO restauranteDTO) {

        if (id <= 0 || id > restaurantes.size()) {
            throw new EntityNotFoundException("Restaurante", id);
        }

        // Simulação de atualização
        restaurantes.set(id.intValue() - 1, restauranteDTO);
        return ResponseEntity.ok(restauranteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable @Positive(message = "ID deve ser positivo") Long id) {

        if (id <= 0 || id > restaurantes.size()) {
            throw new EntityNotFoundException("Restaurante", id);
        }

        restaurantes.remove(id.intValue() - 1);
        return ResponseEntity.noContent().build();
    }
}
