# Projeto Infuse

## Descrição

O Infuse é um projeto desenvolvido por Marco Antonio Moreira (marco.baptista21@gmail.com) e tem como objetivo a solução Java em formato de API para recepção de pedidos de Clientes com os seguintes critérios:

1. Receber pedidos no formato Json e XML.
2. O arquivo pode conter 1 ou mais pedidos, limitado a 10.
3. Não poderá aceitar um número de controle já cadastrado.
4. Caso a data de cadastro não seja enviada o sistema deve assumir a data atual.
5. Caso a quantidade não seja enviada considerar 1.
6. Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, para quantidades a partir de 10 aplicar 10% de desconto no valor total.
7. O sistema deve calcular e gravar o valor total do pedido.

## Pré requisitos

1. Java 8
2. Banco de dados MySql
3. Maven

## Instalação

Para rodar este projeto, é necessário ter uma instância do banco de dados MySQL instalada e em execução. 
No Resources do projeto contém um arquivo script.sql aonde estão os scripts necessários para a criação do Database e das Tabelas.

1. Clone o repositório: https://github.com/MarcoAntonio2109/Infuse.git
2. Em sua IDE de preferência importe um projeto Maven já existente.
3. Selecione o projeto Infuse.
4. Clique na raíz do projeto e execute Maven Clean e na sequência Maven install.

## Execução

1. Para executar o projeto clique com o botão direito do mouse na classe **CrudSbApplication** > **Run As** > **Java Application** 

## REST API Endpoints

Base URL: http://localhost:8080/pedido

|    Método    |    Path    |          Descrição          |
| --------     | --------   | --------                    |
|    POST      | /pedido/pedido-xml     | Realiza pedido com envio de arquivo XML                     |
|    POST      | /pedido     | Realiza pedido via Json                     |
|    GET       | /pedido/pedidos-por-parametros     | Consulta pedido por parametros informados                     |
|    GET       | /pedido/{id}    | Consulta pedido por Id                    |

Base URL: http://localhost:8080/cliente

|    Método    |    Path    |          Descrição          |
| --------     | --------   | --------                    |
|    POST      | /cliente    | Cadastra Cliente                     |
|    PUT      | /cliente     | Atualiza Cliente                     |
|    GET       | /cliente     | Busca todos os clientes                   |
|    GET       | /cliente/{id}    | Consulta cliente por Id                    |
|    DEL       | /cliente/{id}    | Deleta cliente por Id                    |

Base URL: http://localhost:8080/produto

|    Método    |    Path    |          Descrição          |
| --------     | --------   | --------                    |
|    POST      | /produto    | Cadastra Produto                     |
|    PUT      | /produto     | Atualiza Produto                     |
|    GET       | /produto     | Busca todos os produtos                     |
|    GET       | /produto/{id}    | Consulta produto por Id                    |
|    DEL       | /produto/{id}    | Deleta produto por Id                    |

