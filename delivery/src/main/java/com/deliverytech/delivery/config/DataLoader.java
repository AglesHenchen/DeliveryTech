package com.deliverytech.delivery.config;

import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Saved entities (para reutilizar ao criar pedidos)
    private Cliente savedCliente1;
    private Cliente savedCliente2;
    private Restaurante savedRestaurante1;
    private Restaurante savedRestaurante2;
    private Produto savedProduto1;
    private Produto savedProduto2;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO CARGA DE DADOS DE TESTE ===");

        // Limpar dados existentes
        pedidoRepository.deleteAll();
        produtoRepository.deleteAll();
        restauranteRepository.deleteAll();
        clienteRepository.deleteAll();

        // Inserir dados de teste
        inserirClientes();
        inserirRestaurantes();
        inserirProdutos();
        inserirPedidos();

        // Executar testes das consultas
        testarConsultas();

        System.out.println("=== CARGA DE DADOS CONCLUÍDA ===");
    }

    private void inserirClientes() {
        System.out.println("--- Inserindo Clientes ---");
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("11999999999");
        cliente1.setEndereco("Rua A, 123");
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("11888888888");
        cliente2.setEndereco("Rua B, 456");
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("11777777777");
        cliente3.setEndereco("Rua C, 789");
        cliente3.setAtivo(false);

        // Salva individualmente para obter as entidades com ID gerado
        savedCliente1 = clienteRepository.save(cliente1);
        savedCliente2 = clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);

        System.out.println("✓ 3 clientes inseridos");
    }

    private void inserirRestaurantes() {
        System.out.println("--- Inserindo Restaurantes ---");
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Pizza Express");
        restaurante1.setCategoria("Italiana");
        restaurante1.setEndereco("Av. Principal, 100");
        restaurante1.setTelefone("1133333333");
        restaurante1.setTaxaEntrega(new BigDecimal("3.50"));
        restaurante1.setAtivo(true);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Burger King");
        restaurante2.setCategoria("Fast Food");
        restaurante2.setEndereco("Rua Central, 200");
        restaurante2.setTelefone("1144444444");
        restaurante2.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante2.setAtivo(true);

        savedRestaurante1 = restauranteRepository.save(restaurante1);
        savedRestaurante2 = restauranteRepository.save(restaurante2);

        System.out.println("✓ 2 restaurantes inseridos");
    }

    private void inserirProdutos() {
        System.out.println("--- Inserindo Produtos ---");

        // Agora que restaurantes foram salvos, associe os produtos a eles
        Produto produto1 = new Produto();
        produto1.setNome("Pizza Margherita");
        produto1.setPreco(new BigDecimal("30.00"));
        produto1.setCategoria("Pizza");
        produto1.setRestaurante(savedRestaurante1); // associando

        Produto produto2 = new Produto();
        produto2.setNome("Cheeseburger");
        produto2.setPreco(new BigDecimal("20.00"));
        produto2.setCategoria("Hamburguer");
        produto2.setRestaurante(savedRestaurante2); // associando

        savedProduto1 = produtoRepository.save(produto1);
        savedProduto2 = produtoRepository.save(produto2);

        System.out.println("✓ 2 produtos inseridos");
    }

    private void inserirPedidos() {
        System.out.println("--- Inserindo Pedidos ---");

        // Usa as entidades salvas diretamente (não depende de ids fixos)
        if (savedCliente1 == null || savedCliente2 == null) {
            throw new IllegalStateException("Clientes não foram carregados corretamente antes de inserir pedidos");
        }
        if (savedRestaurante1 == null || savedRestaurante2 == null) {
            throw new IllegalStateException("Restaurantes não foram carregados corretamente antes de inserir pedidos");
        }
        if (savedProduto1 == null || savedProduto2 == null) {
            throw new IllegalStateException("Produtos não foram carregados corretamente antes de inserir pedidos");
        }

        Pedido pedido1 = new Pedido();
        pedido1.setCliente(savedCliente1);
        pedido1.setRestaurante(savedRestaurante1);
        pedido1.setProduto(savedProduto1);
        pedido1.setQuantidade(1);
        pedido1.setTotal(savedProduto1.getPreco().multiply(new BigDecimal(pedido1.getQuantidade())));
        pedido1.setData(LocalDateTime.now());

        Pedido pedido2 = new Pedido();
        pedido2.setCliente(savedCliente2);
        pedido2.setRestaurante(savedRestaurante2);
        pedido2.setProduto(savedProduto2);
        pedido2.setQuantidade(1);
        pedido2.setTotal(savedProduto2.getPreco().multiply(new BigDecimal(pedido2.getQuantidade())));
        pedido2.setData(LocalDateTime.now());

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        System.out.println("✓ 2 pedidos inseridos");
    }

    private void testarConsultas() {
        System.out.println("\n=== TESTANDO CONSULTAS DOS REPOSITORIES ===");

        // Teste ClienteRepository
        System.out.println("\n--- Testes ClienteRepository ---");
        var clientePorEmail = clienteRepository.findByEmail("joao@email.com");
        System.out.println("Cliente por email: " +
                (clientePorEmail.isPresent() ? clientePorEmail.get().getNome() : "Não encontrado"));

        var clientesAtivo = clienteRepository.findByAtivoTrue();
        System.out.println("Clientes ativós: " + clientesAtivo.size());

        var clientesPorNome = clienteRepository.findByNomeContainingIgnoreCase("silva");
        System.out.println("Clientes com 'silva' no nome: " + clientesPorNome.size());

        boolean emailExiste = clienteRepository.existsByEmail("maria@email.com");
        System.out.println("Email maria@email.com existe: " + emailExiste);

        // Teste RestauranteRepository
        System.out.println("\n--- Testes RestauranteRepository ---");
        var restaurantePorNome = restauranteRepository.findByNome("Pizza Express");
        System.out.println("Restaurante Pizza Express: " +
                (!restaurantePorNome.isEmpty() ? restaurantePorNome.get(0).getNome() : "Não encontrado"));

        var restaurantesCategoria = restauranteRepository.findByCategoria("Fast Food");
        System.out.println("Restaurantes de categoria 'Fast Food': " + restaurantesCategoria.size());

        // ... você pode continuar com mais testes conforme necessário
    }
}
