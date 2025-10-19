package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        if (pedido.getData() == null) {
            pedido.setData(LocalDateTime.now());
        }
        return pedidoRepository.save(pedido);
    }

    public Pedido update(Long id, Pedido pedidoAtualizado) {
        Optional<Pedido> existente = pedidoRepository.findById(id);
        if (existente.isPresent()) {
            Pedido p = existente.get();
            p.setCliente(pedidoAtualizado.getCliente());
            p.setProduto(pedidoAtualizado.getProduto());
            p.setQuantidade(pedidoAtualizado.getQuantidade());
            p.setData(pedidoAtualizado.getData() != null ? pedidoAtualizado.getData() : p.getData());
            p.setTotal(pedidoAtualizado.getTotal());
            p.setRestaurante(pedidoAtualizado.getRestaurante());
            return pedidoRepository.save(p);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }
}
