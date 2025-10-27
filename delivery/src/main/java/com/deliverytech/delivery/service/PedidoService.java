package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.ItemPedidoDTO;
import com.deliverytech.delivery.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoDTO dto);

    PedidoResponseDTO buscarPedidoPorId(Long id);

    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);

    List<PedidoResponseDTO> buscarPedidosPorRestaurante(Long restauranteId, StatusPedido status);

    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens);

    void cancelarPedido(Long id);

    Page<PedidoResponseDTO> listarPedidos(StatusPedido status, LocalDate dataInicio,
                                          LocalDate dataFim, Pageable pageable);

    PedidoResponseDTO atualizarStatusPedido(Long id, String status);
}
