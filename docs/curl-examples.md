# API de Produtos - Exemplos de Requisições com cURL

Este documento contém exemplos práticos de como consumir a API de Produtos usando cURL.

## Índice
- [Produtos (CRUD)](#produtos-crud)
- [Filtros de Produtos](#filtros-de-produtos)
- [Imagens de Produtos](#imagens-de-produtos)

## Produtos (CRUD)

### Listar Produtos (Paginado)

```bash
curl -X GET "http://localhost:8082/v1/product/find?page=0&size=5&sortBy=nomeProduto" \
  -H "Accept: application/json"
```

### Buscar Produtos por IDs

```bash
curl -X GET "http://localhost:8082/v1/product/find/ids?ids=1,3,5" \
  -H "Accept: application/json"
```

### Cadastrar Produto com Categoria SALADAS

```bash
curl -X POST "http://localhost:8082/v1/product" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "nameProduct": "Salada Mediterrânea",
    "price": 21.90,
    "category": "SALADAS",
    "description": "Mix de folhas, queijo feta, azeitonas, pepino e tomate com molho especial",
    "imageUrl": null,
    "estoque": 20,
    "ativo": true
  }'
```

### Cadastrar Produto\
  -d '{
    "nameProduct": "X-Bacon Duplo",
    "price": 24.90,
    "category": "LANCHE",
    "description": "Hambúrguer com queijo, bacon duplo, alface, tomate e molho especial",
    "estoque": 30,
    "ativo": true
  }'
```

### Atualizar Produto

```bash
curl -X PUT "http://localhost:8082/v1/product/13" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "nameProduct": "X-Bacon Duplo Premium",
    "price": 26.90,
    "category": "LANCHE",
    "description": "Hambúrguer com queijo especial, bacon duplo, alface, tomate e molho premium",
    "estoque": 25,
    "ativo": true
  }'
```

### Excluir Produto

```bash
curl -X DELETE "http://localhost:8082/v1/product/13" \
  -H "Accept: application/json"
```

### Ativar Produto

```bash
curl -X PATCH "http://localhost:8082/v1/product/13/activate" \
  -H "Accept: application/json"
```

### Desativar Produto

```bash
curl -X PATCH "http://localhost:8082/v1/product/13/deactivate" \
  -H "Accept: application/json"
```

### Alterar Status do Produto

```bash
curl -X PATCH "http://localhost:8082/v1/product/13/status?active=true" \
  -H "Accept: application/json"
```

## Filtros de Produtos

### Filtro Avançado

```bash
curl -X GET "http://localhost:8082/v1/product/filter?category=LANCHE&precoMinimo=15&precoMaximo=25&ativo=true&page=0&size=10&sortBy=preco&direction=asc" \
  -H "Accept: application/json"
```

### Filtro por Categoria (Paginado)

```bash
curl -X GET "http://localhost:8082/v1/product/filter/category?category=BEBIDA&page=0&size=10&sortBy=preco" \
  -H "Accept: application/json"
```

### Busca por Categoria (Simples)

```bash
curl -X GET "http://localhost:8082/v1/product/filter/category/SOBREMESA" \
  -H "Accept: application/json"
```

### Filtro por Faixa de Preço

```bash
curl -X GET "http://localhost:8082/v1/product/filter/price?precoMinimo=5&precoMaximo=10&page=0&size=10&sortBy=preco" \
  -H "Accept: application/json"
```

### Filtro por Status

```bash
curl -X GET "http://localhost:8082/v1/product/filter/status?ativo=false&page=0&size=10&sortBy=nomeProduto" \
  -H "Accept: application/json"
```

## Imagens de Produtos

### Upload de Imagem

```bash
curl -X POST "http://localhost:8082/v1/product/1/image" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/caminho/para/imagem.jpg"
```

### Download de Imagem

```bash
curl -X GET "http://localhost:8082/v1/product/1/image" \
  -H "Accept: text/plain"
```

## Testes Completos com Script Shell

Você pode usar o script abaixo para testar toda a API:

```bash
#!/bin/bash

BASE_URL="http://localhost:8082/v1/product"

echo "== Testando API de Produtos =="

# 1. Listar produtos iniciais
echo -e "\n1. Listando produtos iniciais"
curl -s -X GET "$BASE_URL/find?page=0&size=3" | jq

# 2. Criar um novo produto
echo -e "\n2. Criando novo produto"
NOVO_PRODUTO=$(curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "nameProduct": "Produto Teste API",
    "price": 19.90,
    "category": "LANCHE",
    "description": "Produto criado via API para teste",
    "estoque": 10,
    "ativo": true
  }')
echo $NOVO_PRODUTO | jq
PRODUTO_ID=$(echo $NOVO_PRODUTO | jq -r '.id')

# 3. Buscar produto pelo ID
echo -e "\n3. Buscando produto criado pelo ID"
curl -s -X GET "$BASE_URL/find/ids?ids=$PRODUTO_ID" | jq

# 4. Atualizar produto
echo -e "\n4. Atualizando produto"
curl -s -X PUT "$BASE_URL/$PRODUTO_ID" \
  -H "Content-Type: application/json" \
  -d '{
    "nameProduct": "Produto Teste API (Atualizado)",
    "price": 21.90,
    "category": "LANCHE",
    "description": "Produto atualizado via API para teste",
    "estoque": 15,
    "ativo": true
  }' | jq

# 5. Desativar produto
echo -e "\n5. Desativando produto"
curl -s -X PATCH "$BASE_URL/$PRODUTO_ID/deactivate" | jq

# 6. Filtrar por produtos inativos
echo -e "\n6. Filtrando produtos inativos"
curl -s -X GET "$BASE_URL/filter/status?ativo=false" | jq

# 7. Ativar produto
echo -e "\n7. Ativando produto"
curl -s -X PATCH "$BASE_URL/$PRODUTO_ID/activate" | jq

# 8. Excluir produto
echo -e "\n8. Excluindo produto"
curl -s -X DELETE "$BASE_URL/$PRODUTO_ID" | jq

echo -e "\n== Testes concluídos =="
```

Salve como `test-api.sh` e execute com `bash test-api.sh`.
