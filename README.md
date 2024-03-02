# Farmácia Cult
Sistema de gerenciamento de produtos para uma farmácia. Desafio final do Bloco 02 do bootcamp de Java Full-Stack da Generation Brasil.

## O Desafio
Construa a Backend para uma Farmácia com a capacidade de manipular os dados dos Produtos. Os produtos deverão estar classificados por Categoria. Esta atividade precisa ser entregue no tempo de 4 horas e 35 minutos.

## Implementação

### Tempo
O desafio com as funcionalidades solicitadas foi concluído no tempo de 1 hora e 30 minutos.

### Tecnologias usadas
- Java 17
- SpringBoot 3.2.0
- MySQL 8.0
- Maven 4.0.0
- Hibernate
- Lombok 1.18.30

### Melhorias
Além do que foi pedido no desafio - CRUD para gerenciar os Produtos e as Categorias de Produtos - implementei a segurança com o Spring Security, possibilitando a atribuição de Roles para os usuários, o que permite proteger endpoints específicos da aplicação contra o acesso de usuários não autorizados.

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

## Testando a aplicação

Para realização de testes foi usado o Insomnia, mas as funcionalidades podem ser testadas na ferramenta de sua preferência. Para verificar os endpoints disponíveis para teste, acesse as classes no pacote 'com.generation.cultdrugstore.controller'.

## Contribuindo
Se você estiver interessado em contribuir, siga estes passos:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Saiba mais ou entre em contato

[![Projects](https://img.shields.io/badge/Github-Other_Projects-76368c)](https://github.com/als-samara?tab=repositories) • [![LinkedIn](https://img.shields.io/badge/Contact-LinkedIn-0A66C2)](https://www.linkedin.com/in/samara-almeida-als/) • [![Email](https://img.shields.io/badge/Contact-Email-EA4335)](mailto:samaraalmeida379@gmail.com)
