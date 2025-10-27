package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exception.ResourceNotFoundException;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // ===================== Cadastrar =====================
    @Override
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO dto) {
        Restaurante entidade = mapToEntity(dto);
        // Defina valores padrão quando necessário
        if (entidade.getAtivo() == null) {
            entidade.setAtivo(true);
        }
        Restaurante salvo = restauranteRepository.save(entidade);
        return mapToResponseDTO(salvo);
    }

    // ===================== Listar com paginação e filtros =====================
    @Override
    public Page<RestauranteResponseDTO> listarRestaurantes(String categoria, Boolean ativo, Pageable pageable) {
        Page<Restaurante> page;

        if (categoria != null && ativo != null) {
            page = restauranteRepository.findByCategoriaAndAtivo(categoria, ativo, pageable);
        } else if (categoria != null) {
            page = restauranteRepository.findByCategoria(categoria, pageable);
        } else if (ativo != null) {
            page = restauranteRepository.findByAtivo(ativo, pageable);
        } else {
            page = restauranteRepository.findAll(pageable);
        }

        List<RestauranteResponseDTO> content = page.getContent()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    // ===================== Buscar por ID =====================
    @Override
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        Restaurante r = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com id: " + id));
        return mapToResponseDTO(r);
    }

    // ===================== Atualizar =====================
    @Override
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto) {
        Restaurante existente = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com id: " + id));

        // Atualiza campos permitidos
        existente.setNome(dto.getNome());
        existente.setCategoria(dto.getCategoria());
        existente.setEndereco(dto.getEndereco());
        existente.setTelefone(dto.getTelefone());
        existente.setTaxaEntrega(dto.getTaxaEntrega());
        existente.setAtivo(dto.getTempoEntrega() != null ? existente.getAtivo() : existente.getAtivo()); // exemplo: conservar ativo
        // Atualize tempoEntrega, horarioFuncionamento se entidade tiver esses campos
        restauranteRepository.save(existente);
        return mapToResponseDTO(existente);
    }

    // ===================== Alterar status (ativar/desativar) =====================
    @Override
    public RestauranteResponseDTO alterarStatusRestaurante(Long id) {
        Restaurante existente = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com id: " + id));
        existente.setAtivo(!Boolean.TRUE.equals(existente.getAtivo()));
        restauranteRepository.save(existente);
        return mapToResponseDTO(existente);
    }

    // ===================== Buscar por categoria (lista simples) =====================
    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria) {
        List<Restaurante> list = restauranteRepository.findByCategoria(categoria);
        return list.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    // ===================== Calcular taxa de entrega =====================
    @Override
    public BigDecimal calcularTaxaEntrega(Long id, String cep) {
        Restaurante existente = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com id: " + id));

        // Implementação simples: retorna a taxaBase do restaurante.
        // Você pode integrar aqui a lógica real de cálculo por distância/cep.
        BigDecimal taxaBase = existente.getTaxaEntrega();
        if (taxaBase == null) {
            return BigDecimal.ZERO;
        }
        return taxaBase;
    }

    // ===================== Restaurantes próximos (placeholder) =====================
    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesProximos(String cep, Integer raioKm) {
        // Implementação simplificada: retorna todos os restaurantes ativos.
        // OBS: para funcionalidade real, integre geocoding + cálculo de distâncias
        List<Restaurante> todos = restauranteRepository.findByAtivo(true);
        return todos.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    // ===================== Mapeamentos =====================
    private Restaurante mapToEntity(RestauranteDTO dto) {
        Restaurante r = new Restaurante();
        r.setNome(dto.getNome());
        r.setCategoria(dto.getCategoria());
        r.setEndereco(dto.getEndereco());
        r.setTelefone(dto.getTelefone());
        r.setTaxaEntrega(dto.getTaxaEntrega());
        // r.setTempoEntrega(dto.getTempoEntrega()); // descomente se a entidade tiver este campo
        // r.setHorarioFuncionamento(dto.getHorarioFuncionamento()); // idem
        r.setAtivo(true);
        return r;
    }

    private RestauranteResponseDTO mapToResponseDTO(Restaurante r) {
        RestauranteResponseDTO dto = new RestauranteResponseDTO();
        dto.setId(r.getId());
        dto.setNome(r.getNome());
        dto.setCategoria(r.getCategoria());
        dto.setEndereco(r.getEndereco());
        dto.setTelefone(r.getTelefone());
        dto.setTaxaEntrega(r.getTaxaEntrega());
        dto.setAtivo(r.getAtivo());
        // Preencha outros campos conforme necessário
        return dto;
    }
}
