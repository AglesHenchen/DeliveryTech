package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Buscar todos os clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Buscar cliente por ID
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // Criar ou atualizar cliente
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Atualizar cliente existente
    public Cliente update(Long id, Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente c = clienteExistente.get();
            c.setNome(clienteAtualizado.getNome());
            c.setEmail(clienteAtualizado.getEmail());
            c.setTelefone(clienteAtualizado.getTelefone());
            c.setEndereco(clienteAtualizado.getEndereco());
            c.setAtivo(clienteAtualizado.isAtivo());
            return clienteRepository.save(c);
        } else {
            return null; // pode lançar exceção se quiser tratar erro
        }
    }

    // Deletar cliente
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
