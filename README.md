# AgendAI - Sistema de Agendamento com Spring Boot

Este projeto foi desenvolvido com o objetivo de aprender e praticar conceitos de:

- Relacionamentos com JPA
- Spring Security com autenticação JWT
- Controle de acesso por roles (`USER`, `ADMIN`)
- Organização de rotas RESTful
- DTOs (`record`) para respostas limpas e seguras

## 🔐 Roles

- `USER` - Cliente, pode criar e visualizar suas reservas
- `ADMIN` - Empresa, pode gerenciar serviços e visualizar reservas por serviço

## 🚀 Tecnologias

- Java 17+
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT

## 📂 Endpoints

Algumas rotas principais:

| Método | Rota | Acesso |
|--------|------|--------|
| `POST` | `/api/auth/login` | Público |
| `POST` | `/api/auth/register/client` | Público |
| `GET` | `/api/clients/{id}` | USER |
| `POST` | `/api/reservations` | USER |
| `POST` | `/api/services` | ADMIN |

➡️ Veja todas as rotas [aqui](#)

## 📘 Sobre o Projeto

Este projeto não foi feito para produção, mas sim para aprendizado. Me concentrei em entender e aplicar:

- Boas práticas de modelagem de entidades
- Segurança com tokens
- Como estruturar uma API limpa e escalável

[🔗 Meu LinkedIn](https://www.linkedin.com/in/wandlynger-oliveira-9b003b22b/)
