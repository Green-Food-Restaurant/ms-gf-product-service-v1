# 🍔 Microserviço de Gerenciamento de Produtos - FIAP Lanches

Este é o microserviço responsável pelo gerenciamento completo de produtos da aplicação FIAP Lanches.

## 📋 Funcionalidades

### Gerenciamento de Produtos
- **Listagem e Filtros**
  - ✅ Busca Paginada: Implementação de busca com paginação de produtos
  - ✅ Filtros: Permite filtrar produtos por categoria, preço e status (ativo/inativo)
  - ✅ Ordenação: Suporta diferentes critérios de ordenação (nome, preço)

- **Operações CRUD**
  - ✅ Cadastro: Criação de novos produtos com todas as informações (nome, descrição, preço, categoria, imagem, estoque)
  - ✅ Atualização: Modificação de informações de produtos existentes
  - ✅ Exclusão: Remoção de produtos do catálogo
  - ✅ Ativação/Desativação: Alternar o status de um produto sem excluí-lo

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- PostgreSQL
- Docker
- Minio (para armazenamento de imagens)
- Swagger/OpenAPI (documentação)
- Flyway (migrações de banco de dados)

## 🚀 Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- JDK 17+ (para desenvolvimento)
- Maven (para desenvolvimento)

### Executando com Docker
```bash
# Clone o repositório
git clone https://github.com/fiap-lanches/ms-gf-product-service-v1.git
cd ms-gf-product-service-v1

# Execute com Docker Compose
docker-compose up -d
```

A aplicação estará disponível em http://localhost:8082

### Documentação da API
Acesse a documentação Swagger da API em http://localhost:8082/swagger-ui.html

## 📝 Endpoints Principais

### Listagem e Filtros
- `GET /v1/product/find` - Lista todos os produtos com paginação
- `GET /v1/product/filter` - Filtra produtos com múltiplos critérios
- `GET /v1/product/filter/category` - Filtra produtos por categoria
- `GET /v1/product/filter/price` - Filtra produtos por faixa de preço
- `GET /v1/product/filter/status` - Filtra produtos por status (ativo/inativo)

### Operações CRUD
- `POST /v1/product/register` - Cadastra um novo produto
- `PUT /v1/product/update/{id}` - Atualiza um produto existente
- `DELETE /v1/product/delete/{id}` - Remove um produto
- `PATCH /v1/product/{id}/activate` - Ativa um produto
- `PATCH /v1/product/{id}/deactivate` - Desativa um produto

### Manipulação de Imagens
- `POST /v1/product/{id}/image` - Upload de imagem para um produto
- `GET /v1/product/{id}/image` - Download da imagem de um produto

## 🧪 Testes

```bash
# Execute os testes
mvn test
```

## 📊 Banco de Dados

O projeto utiliza PostgreSQL e Flyway para gerenciamento de schema e migrations.

### Estrutura do Banco
- Tabela `product` - Armazena informações dos produtos
- Colunas incluem: id, nome_produto, preco, category, descricao, key_image, url_image, estoque, ativo

## 📚 Arquitetura

Este projeto utiliza arquitetura hexagonal (ports and adapters) para melhor separação de responsabilidades e testabilidade.

### Camadas
- **Domain**: Entidades de domínio e regras de negócio
- **Application**: Use cases e DTOs
- **Infrastructure**: Adaptadores, repositórios e controladores REST
