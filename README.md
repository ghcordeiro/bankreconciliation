# Bank Reconciliation Project

##  Visão Geral
Este projeto realiza a **conciliação bancária e contábil** a partir do upload de arquivos (`.csv`), utilizando uma arquitetura baseada em microsserviços com Spring Boot, R2DBC, Docker Compose.

## Estrutura do Projeto

- `api` — API responsável por receber os arquivos.
- `reconciliation-worker` — Tem um scheduler para buscar dos dados não conciliados e fazer a conciliação.
- `postgres` — Banco de dados principal (R2DBC).

## Tecnologias e Padrões Utilizados

### Backend:
- Java 21
- Spring Boot 3
- Spring WebFlux (para serviços reativos)
- R2DBC + PostgreSQL

### Testes:
- JUnit 5
- Mockito
- Testcontainers (para testes de integração com PostgreSQL)

### Infraestrutura:
- Docker Compose
- PostgreSQL

### Padrões de Projeto:
- SOLID Principles
- Domain-Driven Design (DDD) - aplicado principalmente no `reconciliation-worker`
- Microsserviços desacoplados

## Como Executar o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/ghcordeiro/bank-reconciliation.git
cd bank-reconciliation
```

2. Suba o ambiente com Docker Compose:
```bash
docker-compose up --build
```

## Upload de Arquivos para transações bancárias

**Endpoint:** `POST /bank-transactions/upload`

**Formato:**
- Formulário `multipart/form-data`
- Campo: `file`

**Exemplo via `curl`:**
```bash
curl -F "file=@/caminho/do/arquivo.csv" http://localhost:8080/accounting-transactions/upload
```

## Upload de Arquivos para transações contábeis

**Endpoint:** `POST /bank-transactions/upload`

**Formato:**
- Formulário `multipart/form-data`
- Campo: `file`

**Exemplo via `curl`:**
```bash
curl -F "file=@/caminho/do/arquivo.csv" http://localhost:8080/accounting-transactions/upload
```

## Testes

### Unitários:
- Cobrem serviços, controladores e produção de mensagens.

### Integração:
- Testes com containers PostgreSQL usando Testcontainers.

## Contato
Desenvolvido por [Guilherme Cordeiro](https://github.com/ghcordeiro) — entre em contato para dúvidas, sugestões ou colaborações.

