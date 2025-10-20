package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // GET /restaurantes
    @GetMapping
    public ResponseEntity<List<Restaurante>> getAll() {
        List<Restaurante> lista = restauranteService.findAll();
        return ResponseEntity.ok(lista);
    }

    // GET /restaurantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getById(@PathVariable Long id) {
        Optional<Restaurante> opt = restauranteService.findById(id);
        return opt.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /restaurantes
    @PostMapping
    public ResponseEntity<Restaurante> create(@RequestBody Restaurante restaurante) {
        Restaurante salvo = restauranteService.save(restaurante);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(salvo);
    }

    // PUT /restaurantes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> update(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante atualizado = restauranteService.update(id, restaurante);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /restaurantes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Restaurante> existente = restauranteService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        restauranteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
