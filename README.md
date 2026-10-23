# Smart List API

API para um aplicativo mobile de lista de compras inteligente, desenvolvido em React Native. A proposta é ajudar o usuário a lembrar do que precisa comprar: quando ele sair de casa, o aplicativo poderá avisá-lo sobre os itens pendentes na lista.

## Objetivo

A API dará suporte ao aplicativo mobile e à gestão das listas e dos itens de compra. A experiência planejada inclui um lembrete contextual, apresentado quando o usuário sair de casa, para que ele não esqueça o que precisa comprar.

> **Estado atual:** projeto em fase inicial. As funcionalidades de listas, itens, usuários, localização e notificações ainda não estão implementadas; portanto, não há endpoints de negócio documentados neste momento.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- SQLite JDBC
- Maven (inclui Maven Wrapper)

## Pré-requisitos

- JDK 21

O Maven Wrapper incluído no projeto pode ser usado sem uma instalação global do Maven.

## Executar localmente

No Windows, na pasta do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A configuração inicial define o nome da aplicação como `smart-list-api`. Endpoints e porta serão documentados quando forem implementados.

## Testes

Para executar os testes:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS:

```bash
./mvnw test
```

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/smartlist/smartlistapi/  # Código da aplicação
│   └── resources/                       # Configurações e recursos
└── test/
    └── java/com/smartlist/smartlistapi/ # Testes
```

## Próximos passos

- Definir os recursos e contratos da API para listas e itens de compra.
- Implementar persistência e validação dos dados.
- Integrar o aplicativo mobile com a API.
- Definir como a localização e o aviso ao sair de casa serão tratados entre o aplicativo e a API.