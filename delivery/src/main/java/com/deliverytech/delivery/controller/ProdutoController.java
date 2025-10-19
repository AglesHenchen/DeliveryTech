package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // GET /produtos
    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        List<Produto> lista = produtoService.findAll();
        return ResponseEntity.ok(lista);
    }

    // GET /produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        Optional<Produto> opt = produtoService.findById(id);
        return opt.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /produtos
    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        Produto salvo = produtoService.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(salvo);
    }

    // PUT /produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produto) {
        Produto atualizado = produtoService.update(id, produto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    // DELETE /produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Produto> existente = produtoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
