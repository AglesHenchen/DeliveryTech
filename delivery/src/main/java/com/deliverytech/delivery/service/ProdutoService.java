package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    // ===================== Cadastrar Produto =====================
    public ProdutoResponseDTO cadastrarProduto(ProdutoDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setDisponivel(true); // produto novo sempre disponível
        produto.setRestaurante(restaurante);

        Produto salvo = produtoRepository.save(produto);
        return mapToResponseDTO(salvo);
    }

    // ===================== Buscar por ID =====================
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return mapToResponseDTO(produto);
    }

    // ===================== Atualizar Produto =====================
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setRestaurante(restaurante);

        Produto atualizado = produtoRepository.save(produto);
        return mapToResponseDTO(atualizado);
    }

    // ===================== Remover Produto =====================
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        // Aqui você pode adicionar validação se o produto tiver pedidos associados
        produtoRepository.delete(produto);
    }

    // ===================== Alterar Disponibilidade =====================
    public ProdutoResponseDTO alterarDisponibilidade(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setDisponivel(!produto.isDisponivel());
        Produto atualizado = produtoRepository.save(produto);
        return mapToResponseDTO(atualizado);
    }

    // ===================== Buscar por Categoria =====================
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ===================== Buscar por Nome =====================
    public List<ProdutoResponseDTO> buscarProdutosPorNome(String nome) {
        // Supondo que você vá adicionar método no repository como:
        // List<Produto> findByNomeContainingIgnoreCase(String nome);
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ===================== Mapear Entidade para DTO =====================
    private ProdutoResponseDTO mapToResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setCategoria(produto.getCategoria());
        dto.setDisponivel(produto.isDisponivel());
        dto.setRestauranteId(produto.getRestaurante().getId());
        return dto;
    }

    // ===================== Buscar Produtos por Restaurante com Filtro de
    // Disponibilidade =====================
    public List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId, Boolean disponivel) {
        // Busca os produtos do restaurante (repositório já tem findByRestauranteId)
        List<Produto> produtos = produtoRepository.findByRestauranteId(restauranteId);

        // Se vier filtro de disponibilidade, aplique
        if (disponivel != null) {
            produtos = produtos.stream()
                    .filter(p -> Boolean.valueOf(p.isDisponivel()).equals(disponivel))
                    .collect(Collectors.toList());
        }

        // Mapeia para DTOs
        return produtos.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

}
