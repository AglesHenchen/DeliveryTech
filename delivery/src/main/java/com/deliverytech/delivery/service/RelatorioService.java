package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.VendasPorRestauranteDTO;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Gera relatório de vendas por restaurante entre duas datas.
     */
    public List<VendasPorRestauranteDTO> vendasPorRestaurante(LocalDate dataInicio, LocalDate dataFim) {

        List<Pedido> pedidos = pedidoRepository.findByDataPedidoBetween(
                dataInicio.atStartOfDay(),
                dataFim.atTime(23, 59, 59)
        );

        return pedidos.stream()
                .collect(Collectors.groupingBy(p -> p.getRestaurante().getId()))
                .entrySet().stream()
                .map(entry -> {
                    Long restauranteId = entry.getKey();
                    List<Pedido> pedidosRestaurante = entry.getValue();
                    BigDecimal totalVendas = pedidosRestaurante.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    VendasPorRestauranteDTO dto = new VendasPorRestauranteDTO();
                    dto.setRestauranteId(restauranteId);
                    dto.setNomeRestaurante(pedidosRestaurante.get(0).getRestaurante().getNome());
                    dto.setTotalVendas(totalVendas);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
