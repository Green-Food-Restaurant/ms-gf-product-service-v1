-- Criar a tabela subcategory
CREATE TABLE subcategory (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(255) NOT NULL
    -- Idealmente, category aqui referenciaria uma tabela de categorias separada.
    -- Por ora, manteremos como VARCHAR para alinhar com o Enum Category existente.
    -- A validação da consistência entre product.category e subcategory.category pode ser feita na aplicação.
);

-- Adicionar coluna subcategory_id à tabela product
ALTER TABLE product
ADD COLUMN subcategory_id BIGINT,
ADD CONSTRAINT fk_product_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategory(id);

-- Popular algumas subcategorias de exemplo
INSERT INTO subcategory (name, category) VALUES
('Hambúrgueres Bovinos', 'LANCHE'),
('Hambúrgueres de Frango', 'LANCHE'),
('Vegetarianos', 'LANCHE'),
('Batatas Tradicionais', 'ACOMPANHAMENTO'),
('Anéis de Cebola', 'ACOMPANHAMENTO'),
('Nuggets', 'ACOMPANHAMENTO'),
('Refrigerantes Lata', 'BEBIDA'),
('Sucos Naturais', 'BEBIDA'),
('Água', 'BEBIDA'),
('Bolos e Tortas', 'SOBREMESA'),
('Sorvetes de Massa', 'SOBREMESA'),
('Frutas', 'SOBREMESA'),
('Saladas Verdes', 'SALADAS'),
('Saladas com Proteína', 'SALADAS'),
('Molhos para Salada', 'SALADAS');

-- Atualizar produtos existentes para associar a subcategorias (exemplos)
-- Lanches
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Hambúrgueres Bovinos' AND category = 'LANCHE') WHERE nome_produto = 'Hambúrguer Clássico';
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Hambúrgueres Bovinos' AND category = 'LANCHE') WHERE nome_produto = 'X-Salada Especial';

-- Acompanhamentos
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Batatas Tradicionais' AND category = 'ACOMPANHAMENTO') WHERE nome_produto = 'Batata Frita';

-- Bebidas
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Refrigerantes Lata' AND category = 'BEBIDA') WHERE nome_produto = 'Refrigerante';

-- Sobremesas
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Sorvetes de Massa' AND category = 'SOBREMESA') WHERE nome_produto = 'Sorvete';

-- Saladas
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Saladas com Proteína' AND category = 'SALADAS') WHERE nome_produto = 'Salada Caesar';
UPDATE product SET subcategory_id = (SELECT id FROM subcategory WHERE name = 'Saladas Verdes' AND category = 'SALADAS') WHERE nome_produto = 'Salada de Frutas';
