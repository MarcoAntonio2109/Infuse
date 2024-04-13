CREATE DATABASE infuse;

CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    sobrenome VARCHAR(255),
    cpf VARCHAR(20) NOT NULL,
    data_nascimento DATE
);

CREATE TABLE Pedido (
    id_pedido BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    num_controle BIGINT,
    data_cadastro DATE,
    nome VARCHAR(255),
    valor DECIMAL(10, 2),
    quantidade INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
	FOREIGN KEY (id_produto) REFERENCES Produto(id_produto)
);

CREATE TABLE produto (
    id_produto BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    preco DOUBLE
);

DROP TABLE IF EXISTS Pedido;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Produto;
