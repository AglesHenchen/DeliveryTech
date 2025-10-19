-- Inserir dados na tabela restaurante
INSERT INTO restaurante (nome, endereco, categoria, telefone, taxa_entrega, ativo) VALUES
('Restaurante Saboroso', 'Rua das Flores, 100', 'Comida Brasileira', '11987654321', 5.50, TRUE),
('Pizzaria Bella', 'Avenida Paulista, 2000', 'Pizzaria', '11987654322', 7.00, TRUE),
('Churrascaria Gaúcha', 'Rua do Sul, 150', 'Churrascaria', '11987654323', 10.00, FALSE),
('Sushi House', 'Rua do Japão, 777', 'Sushi', '11987654324', 8.50, TRUE);

-- Inserir dados na tabela produto
INSERT INTO produto (nome, preco, categoria, restaurante_id) VALUES
('Feijoada Completa', 45.00, 'Prato Principal', 1),
('Pizza Margherita', 32.00, 'Pizza', 2),
('Picanha na Brasa', 55.00, 'Churrasco', 3),
('Sushi Roll', 28.00, 'Sushi', 4);

-- Inserir clientes
INSERT INTO cliente (nome, email, telefone, endereco, ativo) VALUES
('João Silva', 'joao.silva@example.com', '11987654321', 'Rua A, 123', TRUE),
('Maria Oliveira', 'maria.oliveira@example.com', '11987654322', 'Rua B, 456', TRUE),
('Carlos Souza', 'carlos.souza@example.com', '11987654323', 'Rua C, 789', FALSE),
('Ana Costa', 'ana.costa@example.com', '11987654324', 'Rua D, 101', TRUE);

-- Inserir pedidos
INSERT INTO pedido (cliente_id, produto_id, restaurante_id, data_pedido, quantidade, total) VALUES
(1, 1, 1, CURRENT_TIMESTAMP, 2, 70.00),
(2, 2, 2, CURRENT_TIMESTAMP, 1, 20.00),
(3, 3, 3, CURRENT_TIMESTAMP, 3, 135.00);
