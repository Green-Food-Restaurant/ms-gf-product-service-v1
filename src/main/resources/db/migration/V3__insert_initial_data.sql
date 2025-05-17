-- Adiciona alguns produtos iniciais para testes

-- Produtos da categoria LANCHE
INSERT INTO product (nome_produto, preco, category, descricao, estoque, ativo)
VALUES 
('Hambúrguer Clássico', 18.90, 'LANCHE', 'Hambúrguer com queijo, alface, tomate e molho especial', 50, true),
('X-Bacon', 22.90, 'LANCHE', 'Hambúrguer com queijo, bacon crocante, alface, tomate e molho especial', 50, true),
('Veggie Burger', 19.90, 'LANCHE', 'Hambúrguer vegetal com queijo, alface, tomate e molho especial', 30, true);

-- Produtos da categoria ACOMPANHAMENTO
INSERT INTO product (nome_produto, preco, category, descricao, estoque, ativo)
VALUES 
('Batata Frita', 9.90, 'ACOMPANHAMENTO', 'Porção de batatas fritas crocantes', 100, true),
('Onion Rings', 12.90, 'ACOMPANHAMENTO', 'Anéis de cebola empanados', 80, true),
('Nuggets', 14.90, 'ACOMPANHAMENTO', 'Porção de nuggets de frango', 80, true);

-- Produtos da categoria BEBIDA
INSERT INTO product (nome_produto, preco, category, descricao, estoque, ativo)
VALUES 
('Refrigerante 350ml', 6.90, 'BEBIDA', 'Refrigerante em lata', 200, true),
('Suco Natural', 8.90, 'BEBIDA', 'Suco natural de laranja, limão ou maracujá', 150, true),
('Água Mineral', 4.90, 'BEBIDA', 'Água mineral sem gás 500ml', 200, true);

-- Produtos da categoria SOBREMESA
INSERT INTO product (nome_produto, preco, category, descricao, estoque, ativo)
VALUES 
('Sorvete', 7.90, 'SOBREMESA', 'Sorvete de creme, chocolate ou morango', 100, true),
('Pudim', 8.90, 'SOBREMESA', 'Pudim de leite condensado', 50, true),
('Brownie', 9.90, 'SOBREMESA', 'Brownie de chocolate com sorvete', 60, true);
