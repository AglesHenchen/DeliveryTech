package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto update(Long id, Produto produtoAtualizado) {
        Optional<Produto> existente = produtoRepository.findById(id);
        if (existente.isPresent()) {
            Produto p = existente.get();
            p.setNome(produtoAtualizado.getNome());
            p.setPreco(produtoAtualizado.getPreco());
            p.setRestaurante(produtoAtualizado.getRestaurante());
            p.setCategoria(produtoAtualizado.getCategoria());
            return produtoRepository.save(p);
        } else {
            return null; // você pode preferir lançar exceção em vez de retornar null
        }
    }

    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
