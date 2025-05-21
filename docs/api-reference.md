# API de Gerenciamento de Produtos - Documentação Funcional

## Índice
- [Visão Geral](#visão-geral)
- [Produtos (CRUD)](#produtos-crud)
  - [Listar Produtos](#listar-produtos)
  - [Buscar Produtos por IDs](#buscar-produtos-por-ids)
  - [Cadastrar Produto](#cadastrar-produto)
  - [Atualizar Produto](#atualizar-produto)
  - [Excluir Produto](#excluir-produto)
  - [Ativar Produto](#ativar-produto)
  - [Desativar Produto](#desativar-produto)
  - [Alterar Status do Produto](#alterar-status-do-produto)
- [Filtros de Produtos](#filtros-de-produtos)
  - [Filtro Avançado](#filtro-avançado)
  - [Filtro por Categoria (Paginado)](#filtro-por-categoria-paginado)
  - [Busca por Categoria (Simples)](#busca-por-categoria-simples)
  - [Filtro por Faixa de Preço](#filtro-por-faixa-de-preço)
  - [Filtro por Status](#filtro-por-status)
- [Imagens de Produtos](#imagens-de-produtos)
  - [Upload de Imagem](#upload-de-imagem)
  - [Download de Imagem](#download-de-imagem)
- [Modelos de Dados](#modelos-de-dados)
  - [Produto (Response)](#produto-response)
  - [Produto (Request)](#produto-request)
  - [Enumerações](#enumerações)

## Visão Geral

A API de Gerenciamento de Produtos permite realizar operações completas para cadastro, atualização, exclusão e busca de produtos, além de funcionalidades para ativar/desativar produtos e gerenciar imagens.

- **URL Base**: `/v1/product`
- **Content-Type**: `application/json`
- **Autenticação**: Não implementada nesta versão

## Produtos (CRUD)

### Listar Produtos

Lista todos os produtos com suporte a paginação e ordenação.

- **URL**: `/v1/product/find`
- **Método**: `GET`
- **Parâmetros**:
  - `page` (query, opcional): Número da página (default: 0)
  - `size` (query, opcional): Tamanho da página (default: 10)
  - `sortBy` (query, opcional): Campo para ordenação (default: "id")

**Exemplo de Request:**
```
GET /v1/product/find?page=0&size=5&sortBy=nomeProduto
```

**Exemplo de Response:**
```json
{
  "content": [
    {
      "id": 1,
      "nomeProduto": "Hambúrguer Clássico",
      "preco": 18.90,
      "category": "LANCHE",
      "descricao": "Hambúrguer com queijo, alface, tomate e molho especial",
      "urlImage": "https://storage.example.com/images/hamburguer-classico.jpg",
      "estoque": 50,
      "ativo": true
    },
    {
      "id": 3,
      "nomeProduto": "Batata Frita",
      "preco": 9.90,
      "category": "ACOMPANHAMENTO",
      "descricao": "Porção de batatas fritas crocantes",
      "urlImage": "https://storage.example.com/images/batata-frita.jpg",
      "estoque": 100,
      "ativo": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 3,
  "totalElements": 12,
  "last": false,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 5,
  "first": true,
  "empty": false
}
```

### Buscar Produtos por IDs

Retorna produtos específicos a partir de seus IDs.

- **URL**: `/v1/product/find/ids`
- **Método**: `GET`
- **Parâmetros**:
  - `ids` (query, obrigatório): Lista de IDs de produtos

**Exemplo de Request:**
```
GET /v1/product/find/ids?ids=1,3,5
```

**Exemplo de Response:**
```json
[
  {
    "id": 1,
    "nomeProduto": "Hambúrguer Clássico",
    "preco": 18.90,
    "category": "LANCHE",
    "descricao": "Hambúrguer com queijo, alface, tomate e molho especial",
    "urlImage": "https://storage.example.com/images/hamburguer-classico.jpg",
    "estoque": 50,
    "ativo": true
  },
  {
    "id": 3,
    "nomeProduto": "Batata Frita",
    "preco": 9.90,
    "category": "ACOMPANHAMENTO",
    "descricao": "Porção de batatas fritas crocantes",
    "urlImage": "https://storage.example.com/images/batata-frita.jpg",
    "estoque": 100,
    "ativo": true
  },
  {
    "id": 5,
    "nomeProduto": "Refrigerante 350ml",
    "preco": 6.90,
    "category": "BEBIDA",
    "descricao": "Refrigerante em lata",
    "urlImage": "https://storage.example.com/images/refrigerante.jpg",
    "estoque": 200,
    "ativo": true
  }
]
```

### Cadastrar Produto

Cadastra um novo produto no sistema.

- **URL**: `/v1/product`
- **Método**: `POST`
- **Body**: Objeto [Produto (Request)](#produto-request)

**Exemplo de Request:**
```json
{
  "nameProduct": "X-Bacon Duplo",
  "price": 24.90,
  "category": "LANCHE",
  "description": "Hambúrguer com queijo, bacon duplo, alface, tomate e molho especial",
  "imageUrl": null,
  "estoque": 30,
  "ativo": true
}
```

**Exemplo de Response:**
```json
{
  "id": 13,
  "nomeProduto": "X-Bacon Duplo",
  "preco": 24.90,
  "category": "LANCHE",
  "descricao": "Hambúrguer com queijo, bacon duplo, alface, tomate e molho especial",
  "urlImage": null,
  "estoque": 30,
  "ativo": true
}
```

### Atualizar Produto

Atualiza as informações de um produto existente.

- **URL**: `/v1/product/{id}`
- **Método**: `PUT`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto a ser atualizado
- **Body**: Objeto [Produto (Request)](#produto-request)

**Exemplo de Request:**
```json
{
  "nameProduct": "X-Bacon Duplo Premium",
  "price": 26.90,
  "category": "LANCHE",
  "description": "Hambúrguer com queijo especial, bacon duplo, alface, tomate e molho premium",
  "imageUrl": null,
  "estoque": 25,
  "ativo": true
}
```

**Exemplo de Response:**
```json
{
  "id": 13,
  "nomeProduto": "X-Bacon Duplo Premium",
  "preco": 26.90,
  "category": "LANCHE",
  "descricao": "Hambúrguer com queijo especial, bacon duplo, alface, tomate e molho premium",
  "urlImage": null,
  "estoque": 25,
  "ativo": true
}
```

### Excluir Produto

Remove um produto do catálogo.

- **URL**: `/v1/product/{id}`
- **Método**: `DELETE`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto a ser excluído

**Exemplo de Request:**
```
DELETE /v1/product/13
```

**Exemplo de Response:**
```json
"Produto removido com sucesso!"
```

### Ativar Produto

Ativa um produto que estava desativado.

- **URL**: `/v1/product/{id}/activate`
- **Método**: `PATCH`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto a ser ativado

**Exemplo de Request:**
```
PATCH /v1/product/13/activate
```

**Exemplo de Response:**
```json
{
  "id": 13,
  "productName": "X-Bacon",
  "productDescription": "Hambúrguer, bacon, queijo, alface, tomate, picles",
  "productPrice": 19.90,
  "category": "LANCHE",
  "imageUrl": "https://bucket-url/images/x-bacon.jpg",
  "stock": 50,
  "active": true,
  "sku": "LCH-XBN-001",
  "barcode": "789012345678",
  "subcategory": {
    "id": 1,
    "name": "Hambúrgueres Premium"
  }
}
```

### Desativar Produto

Desativa um produto sem excluí-lo do catálogo.

- **URL**: `/v1/product/{id}/deactivate`
- **Método**: `PATCH`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto a ser desativado

**Exemplo de Request:**
```
PATCH /v1/product/13/deactivate
```

**Exemplo de Response:**
```json
{
  "id": 13,
  "productName": "X-Bacon",
  "productDescription": "Hambúrguer, bacon, queijo, alface, tomate, picles",
  "productPrice": 19.90,
  "category": "LANCHE",
  "imageUrl": "https://bucket-url/images/x-bacon.jpg",
  "stock": 50,
  "active": false,
  "sku": "LCH-XBN-001",
  "barcode": "789012345678",
  "subcategory": {
    "id": 1,
    "name": "Hambúrgueres Premium"
  }
}
```

### Alterar Status do Produto

Ativa ou desativa um produto baseado no valor booleano fornecido.

- **URL**: `/v1/product/{id}/status`
- **Método**: `PATCH`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto
  - `active` (query, obrigatório): Status desejado (true para ativar, false para desativar)

**Exemplo de Request:**
```
PATCH /v1/product/13/status?active=true
```

**Exemplo de Response:**
```json
{
  "id": 13,
  "productName": "X-Bacon",
  "productDescription": "Hambúrguer, bacon, queijo, alface, tomate, picles",
  "productPrice": 19.90,
  "category": "LANCHE",
  "imageUrl": "https://bucket-url/images/x-bacon.jpg",
  "stock": 50,
  "active": true,
  "sku": "LCH-XBN-001",
  "barcode": "789012345678",
  "subcategory": {
    "id": 1,
    "name": "Hambúrgueres Premium"
  }
}
```

## Filtros de Produtos

### Filtro Avançado

Filtra produtos com base em múltiplos critérios combinados.

- **URL**: `/v1/product/filter`
- **Método**: `GET`
- **Parâmetros**:
  - `category` (query, opcional): Categoria do produto (valores: LANCHE, ACOMPANHAMENTO, BEBIDA, SOBREMESA, SALADAS)
  - `precoMinimo` (query, opcional): Preço mínimo
  - `precoMaximo` (query, opcional): Preço máximo
  - `ativo` (query, opcional): Status de ativação (true/false)
  - `page` (query, opcional): Número da página (default: 0)
  - `size` (query, opcional): Tamanho da página (default: 10)
  - `sortBy` (query, opcional): Campo para ordenação (default: "id")
  - `direction` (query, opcional): Direção da ordenação (asc/desc, default: "asc")

**Observação**: Em caso de uma categoria inválida, a API retornará uma lista vazia em vez de um erro.

**Exemplo de Request:**
```
GET /v1/product/filter?category=LANCHE&precoMinimo=15&precoMaximo=25&ativo=true&page=0&size=10&sortBy=preco&direction=asc
```

**Exemplo de Response:**
```json
{
  "content": [
    {
      "id": 1,
      "nomeProduto": "Hambúrguer Clássico",
      "preco": 18.90,
      "category": "LANCHE",
      "descricao": "Hambúrguer com queijo, alface, tomate e molho especial",
      "urlImage": "https://storage.example.com/images/hamburguer-classico.jpg",
      "estoque": 50,
      "ativo": true
    },
    {
      "id": 2,
      "nomeProduto": "X-Bacon",
      "preco": 22.90,
      "category": "LANCHE",
      "descricao": "Hambúrguer com queijo, bacon crocante, alface, tomate e molho especial",
      "urlImage": "https://storage.example.com/images/x-bacon.jpg",
      "estoque": 50,
      "ativo": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 2,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

### Filtro por Categoria (Paginado)

Busca produtos por categoria com suporte a paginação.

- **URL**: `/v1/product/filter/category`
- **Método**: `GET`
- **Parâmetros**:
  - `category` (query, obrigatório): Categoria do produto
  - `page` (query, opcional): Número da página (default: 0)
  - `size` (query, opcional): Tamanho da página (default: 10)
  - `sortBy` (query, opcional): Campo para ordenação (default: "nomeProduto")

**Exemplo de Request:**
```
GET /v1/product/filter/category?category=BEBIDA&page=0&size=10&sortBy=preco
```

**Exemplo de Response:**
```json
{
  "content": [
    {
      "id": 9,
      "nomeProduto": "Água Mineral",
      "preco": 4.90,
      "category": "BEBIDA",
      "descricao": "Água mineral sem gás 500ml",
      "urlImage": "https://storage.example.com/images/agua.jpg",
      "estoque": 200,
      "ativo": true
    },
    {
      "id": 7,
      "nomeProduto": "Refrigerante 350ml",
      "preco": 6.90,
      "category": "BEBIDA",
      "descricao": "Refrigerante em lata",
      "urlImage": "https://storage.example.com/images/refrigerante.jpg",
      "estoque": 200,
      "ativo": true
    },
    {
      "id": 8,
      "nomeProduto": "Suco Natural",
      "preco": 8.90,
      "category": "BEBIDA",
      "descricao": "Suco natural de laranja, limão ou maracujá",
      "urlImage": "https://storage.example.com/images/suco.jpg",
      "estoque": 150,
      "ativo": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 3,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 3,
  "first": true,
  "empty": false
}
```

### Busca por Categoria (Simples)

Retorna todos os produtos de uma categoria específica.

- **URL**: `/v1/product/filter/category/{category}`
- **Método**: `GET`
- **Parâmetros**:
  - `category` (path, obrigatório): Categoria do produto

**Exemplo de Request:**
```
GET /v1/product/filter/category/SOBREMESA
```

**Exemplo de Response:**
```json
[
  {
    "id": 10,
    "nomeProduto": "Sorvete",
    "preco": 7.90,
    "category": "SOBREMESA",
    "descricao": "Sorvete de creme, chocolate ou morango",
    "urlImage": "https://storage.example.com/images/sorvete.jpg",
    "estoque": 100,
    "ativo": true
  },
  {
    "id": 11,
    "nomeProduto": "Pudim",
    "preco": 8.90,
    "category": "SOBREMESA",
    "descricao": "Pudim de leite condensado",
    "urlImage": "https://storage.example.com/images/pudim.jpg",
    "estoque": 50,
    "ativo": true
  },
  {
    "id": 12,
    "nomeProduto": "Brownie",
    "preco": 9.90,
    "category": "SOBREMESA",
    "descricao": "Brownie de chocolate com sorvete",
    "urlImage": "https://storage.example.com/images/brownie.jpg",
    "estoque": 60,
    "ativo": true
  }
]
```

### Filtro por Faixa de Preço

Busca produtos dentro de uma faixa de preço específica.

- **URL**: `/v1/product/filter/price`
- **Método**: `GET`
- **Parâmetros**:
  - `precoMinimo` (query, obrigatório): Preço mínimo
  - `precoMaximo` (query, obrigatório): Preço máximo
  - `page` (query, opcional): Número da página (default: 0)
  - `size` (query, opcional): Tamanho da página (default: 10)
  - `sortBy` (query, opcional): Campo para ordenação (default: "preco")

**Exemplo de Request:**
```
GET /v1/product/filter/price?precoMinimo=5&precoMaximo=10&page=0&size=10&sortBy=preco
```

**Exemplo de Response:**
```json
{
  "content": [
    {
      "id": 7,
      "nomeProduto": "Refrigerante 350ml",
      "preco": 6.90,
      "category": "BEBIDA",
      "descricao": "Refrigerante em lata",
      "urlImage": "https://storage.example.com/images/refrigerante.jpg",
      "estoque": 200,
      "ativo": true
    },
    {
      "id": 10,
      "nomeProduto": "Sorvete",
      "preco": 7.90,
      "category": "SOBREMESA",
      "descricao": "Sorvete de creme, chocolate ou morango",
      "urlImage": "https://storage.example.com/images/sorvete.jpg",
      "estoque": 100,
      "ativo": true
    },
    {
      "id": 8,
      "nomeProduto": "Suco Natural",
      "preco": 8.90,
      "category": "BEBIDA",
      "descricao": "Suco natural de laranja, limão ou maracujá",
      "urlImage": "https://storage.example.com/images/suco.jpg",
      "estoque": 150,
      "ativo": true
    },
    {
      "id": 11,
      "nomeProduto": "Pudim",
      "preco": 8.90,
      "category": "SOBREMESA",
      "descricao": "Pudim de leite condensado",
      "urlImage": "https://storage.example.com/images/pudim.jpg",
      "estoque": 50,
      "ativo": true
    },
    {
      "id": 3,
      "nomeProduto": "Batata Frita",
      "preco": 9.90,
      "category": "ACOMPANHAMENTO",
      "descricao": "Porção de batatas fritas crocantes",
      "urlImage": "https://storage.example.com/images/batata-frita.jpg",
      "estoque": 100,
      "ativo": true
    },
    {
      "id": 12,
      "nomeProduto": "Brownie",
      "preco": 9.90,
      "category": "SOBREMESA",
      "descricao": "Brownie de chocolate com sorvete",
      "urlImage": "https://storage.example.com/images/brownie.jpg",
      "estoque": 60,
      "ativo": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 6,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 6,
  "first": true,
  "empty": false
}
```

### Filtro por Status

Busca produtos ativos ou inativos.

- **URL**: `/v1/product/filter/status`
- **Método**: `GET`
- **Parâmetros**:
  - `ativo` (query, obrigatório): Status de ativação (true/false)
  - `page` (query, opcional): Número da página (default: 0)
  - `size` (query, opcional): Tamanho da página (default: 10)
  - `sortBy` (query, opcional): Campo para ordenação (default: "id")

**Exemplo de Request:**
```
GET /v1/product/filter/status?ativo=false&page=0&size=10&sortBy=nomeProduto
```

**Exemplo de Response:**
```json
{
  "content": [
    {
      "id": 14,
      "nomeProduto": "Milk Shake",
      "preco": 12.90,
      "category": "SOBREMESA",
      "descricao": "Milk shake de chocolate, morango ou baunilha",
      "urlImage": null,
      "estoque": 0,
      "ativo": false
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

## Imagens de Produtos

### Upload de Imagem

Faz upload de uma imagem para um produto específico.

- **URL**: `/v1/product/{id}/image`
- **Método**: `POST`
- **Content-Type**: `multipart/form-data`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto
  - `file` (form-data, obrigatório): Arquivo de imagem

**Exemplo de Request:**
```
POST /v1/product/1/image
Content-Type: multipart/form-data
[Binary data]
```

**Exemplo de Response:**
```
HTTP/1.1 204 No Content
```

### Download de Imagem

Obtém a URL da imagem de um produto específico.

- **URL**: `/v1/product/{id}/image`
- **Método**: `GET`
- **Parâmetros**:
  - `id` (path, obrigatório): ID do produto

**Exemplo de Request:**
```
GET /v1/product/1/image
```

**Exemplo de Response:**
```
https://storage.example.com/images/hamburguer-classico.jpg
```

## Modelos de Dados

### Produto (Response)

```json
{
  "id": 1,                                                        // ID único do produto
  "nomeProduto": "Hambúrguer Clássico",                          // Nome do produto
  "preco": 18.90,                                                 // Preço do produto
  "category": "LANCHE",                                          // Categoria (LANCHE, ACOMPANHAMENTO, BEBIDA, SOBREMESA)
  "descricao": "Hambúrguer com queijo, alface, tomate e molho",  // Descrição do produto
  "urlImage": "https://storage.example.com/images/hamburguer.jpg", // URL da imagem
  "estoque": 50,                                                  // Quantidade em estoque
  "ativo": true                                                   // Status de ativação
}
```

### Produto (Request)

```json
{
  "nameProduct": "Hambúrguer Clássico",                          // Nome do produto
  "price": 18.90,                                                 // Preço do produto
  "category": "LANCHE",                                          // Categoria
  "description": "Hambúrguer com queijo, alface, tomate e molho", // Descrição do produto
  "imageUrl": "https://storage.example.com/images/hamburguer.jpg", // URL da imagem (opcional)
  "estoque": 50,                                                  // Quantidade em estoque
  "ativo": true                                                   // Status de ativação (opcional, default: true)
}
```

### Enumerações

#### Categorias de Produtos

- `LANCHE`: Lanches (hambúrgueres, sanduíches, etc.)
- `ACOMPANHAMENTO`: Acompanhamentos (batata frita, onion rings, etc.)
- `BEBIDA`: Bebidas (refrigerantes, sucos, água, etc.)
- `SOBREMESA`: Sobremesas (sorvetes, doces, etc.)
- `SALADAS`: Saladas e opções vegetarianas
