-- Tabela: restaurante
CREATE TABLE restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    categoria VARCHAR(100),
    telefone VARCHAR(20),
    taxa_entrega DECIMAL(10, 2),
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela: cliente
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela: produto
CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(100),
    restaurante_id BIGINT,
    FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);


-- Tabela: pedido
CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);
