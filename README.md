# Farmácia Cult
Como desafio final do bloco 02 do bootcamp de desenvolvimento Full-Stack da Generation Brasil, foi desenvolvido em Java este sistema de gerenciamento de produtos para uma farmácia.

## Tecnologias usadas
- Java 17
- SpringBoot 3.2.0
- MySQL 8.0
- Maven 4.0.0
- Hibernate
- Lombok 1.18.30

## Pré-requisitos
Antes de começar, certifique-se de ter instalado:

- Java JDK 11 ou superior
- MySQL
- Maven
- Lombok

## Configuração

1. Clone o repositório:

```
git clone https://github.com/als-samara/cult-drugstore
```

2. Configure o banco de dados no arquivo src/main/resources/application.properties.

```properties
spring.datasource.url=jdbc:mysql://localhost/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

3. Execute o aplicativo:

```
mvn spring-boot:run
```

O aplicativo estará acessível em http://localhost:8080.

## Funcionalidades

- Categorias:
  - Listar todas as categorias
  - Obter detalhes de uma categoria específica por ID
  - Obter detalhes de uma categoria específica por Descrição
  - Criar uma nova categoria
  - Atualizar uma categoria existente
  - Excluir uma categoria

- Produtos:
  - Listar todos os produtos
  - Obter detalhes de um produto específico por ID
  - Obter detalhes de um produto específico pelo Nome
  - Consultar produtos abaixo ou acima de um preço de referência
  - Criar um novo produto
  - Atualizar um produto existente
  - Excluir um produto
