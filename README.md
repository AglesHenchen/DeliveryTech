# Delivery Tech API - AGLES HENCHEN
Sistema de delivery desenvolvido com Spring Boot e Java

## Tecnologias
- **Java 25 LTS**
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## Como executar
1. **Pré-requisitos:** JDK 25 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8080/health

## Endpoints
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações da aplicação
- GET /h2-console - Console do banco H2

## Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development

## Desenvolvedor
[Agles Henchen] - [Arquitetura de Sistemas - T03362]
Desenvolvido com JDK 25 e Spring Boot 3.2.x 
