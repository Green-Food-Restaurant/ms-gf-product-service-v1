-- Atualizar a restrição de verificação para incluir a nova categoria SALADAS
ALTER TABLE product DROP CONSTRAINT IF EXISTS product_category_check;

-- Recria a restrição com o valor SALADAS incluído
ALTER TABLE product ADD CONSTRAINT product_category_check 
CHECK (category IN ('LANCHE', 'ACOMPANHAMENTO', 'BEBIDA', 'SOBREMESA', 'SALADAS'));

-- Adiciona um produto de exemplo na nova categoria
INSERT INTO product (nome_produto, preco, category, descricao, estoque, ativo)
VALUES 
('Salada Caesar', 16.90, 'SALADAS', 'Salada com alface, croutons, queijo parmesão e molho caesar', 30, true),
('Salada Tropical', 18.90, 'SALADAS', 'Mix de folhas verdes com manga, abacaxi e molho de iogurte', 25, true);
