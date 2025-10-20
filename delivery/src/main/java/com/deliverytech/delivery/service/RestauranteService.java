package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> findById(Long id) {
        return restauranteRepository.findById(id);
    }

    public Restaurante save(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public Restaurante update(Long id, Restaurante restauranteAtualizado) {
        Optional<Restaurante> existente = restauranteRepository.findById(id);
        if (existente.isPresent()) {
            Restaurante r = existente.get();
            r.setNome(restauranteAtualizado.getNome());
            r.setEndereco(restauranteAtualizado.getEndereco());
            r.setCategoria(restauranteAtualizado.getCategoria());
            r.setTelefone(restauranteAtualizado.getTelefone());
            r.setTaxaEntrega(restauranteAtualizado.getTaxaEntrega());
            r.setAtivo(restauranteAtualizado.getAtivo());
            return restauranteRepository.save(r);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        restauranteRepository.deleteById(id);
    }

    public List<Produto> buscarProdutosPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId); // Consulta os produtos pelo restaurante
    }
}
