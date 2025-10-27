package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.ItemPedidoDTO;
import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.exception.EntityNotFoundException;
import com.deliverytech.delivery.repository.*;
import com.deliverytech.delivery.service.PedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (!cliente.isAtivo()) {
            throw new BusinessException("Cliente inativo não pode fazer pedidos");
        }

        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));

        if (!restaurante.isAtivo()) {
            throw new BusinessException("Restaurante não está disponível");
        }

        List<ItemPedido> itensPedido = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + itemDTO.getProdutoId()));

            if (!produto.isDisponivel()) {
                throw new BusinessException("Produto indisponível: " + produto.getNome());
            }

            if (!produto.getRestaurante().getId().equals(dto.getRestauranteId())) {
                throw new BusinessException("Produto não pertence ao restaurante selecionado");
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));

            itensPedido.add(item);
            subtotal = subtotal.add(item.getSubtotal());
        }

        BigDecimal taxaEntrega = restaurante.getTaxaEntrega();
        BigDecimal valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setSubtotal(subtotal);
        pedido.setTaxaEntrega(taxaEntrega);
        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (ItemPedido item : itensPedido) {
            item.setPedido(pedidoSalvo);
        }
        pedidoSalvo.setItens(itensPedido);

        return modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        return pedidos.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorRestaurante(Long restauranteId, StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.findByRestauranteIdAndStatus(restauranteId, status);

        return pedidos.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (!isTransicaoValida(pedido.getStatus(), novoStatus)) {
            throw new BusinessException("Transição de status inválida: " +
                    pedido.getStatus() + " -> " + novoStatus);
        }

        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return modelMapper.map(pedidoAtualizado, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO atualizarStatusPedido(Long id, String status) {
        StatusPedido statusPedido;
        try {
            statusPedido = StatusPedido.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido: " + status);
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (!isTransicaoValida(pedido.getStatus(), statusPedido)) {
            throw new BusinessException("Transição de status inválida: " +
                    pedido.getStatus() + " -> " + statusPedido);
        }

        pedido.setStatus(statusPedido);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return modelMapper.map(pedidoAtualizado, PedidoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoDTO item : itens) {
            Produto produto = produtoRepository.findById(item.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        return total;
    }

    @Override
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (!podeSerCancelado(pedido.getStatus())) {
            throw new BusinessException("Pedido não pode ser cancelado no status: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> listarPedidos(StatusPedido status, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);

        Page<Pedido> pedidosPage = pedidoRepository.findByStatusAndDataPedidoBetween(status, inicio, fim, pageable);

        return pedidosPage.map(pedido -> modelMapper.map(pedido, PedidoResponseDTO.class));
    }

    private boolean isTransicaoValida(StatusPedido statusAtual, StatusPedido novoStatus) {
        switch (statusAtual) {
            case PENDENTE:
                return novoStatus == StatusPedido.CONFIRMADO || novoStatus == StatusPedido.CANCELADO;
            case CONFIRMADO:
                return novoStatus == StatusPedido.PREPARANDO || novoStatus == StatusPedido.CANCELADO;
            case PREPARANDO:
                return novoStatus == StatusPedido.SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA:
                return novoStatus == StatusPedido.ENTREGUE;
            default:
                return false;
        }
    }

    private boolean podeSerCancelado(StatusPedido status) {
        return status == StatusPedido.PENDENTE || status == StatusPedido.CONFIRMADO;
    }
}
