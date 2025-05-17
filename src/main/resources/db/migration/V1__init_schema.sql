-- Criação da tabela 'product' se ela não existir
CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    descricao TEXT,
    key_image VARCHAR(255),
    url_image VARCHAR(1000),
    estoque INTEGER DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE
);

-- Criação de índices para melhorar a performance das consultas
CREATE INDEX IF NOT EXISTS idx_product_category ON product(category);
CREATE INDEX IF NOT EXISTS idx_product_ativo ON product(ativo);
CREATE INDEX IF NOT EXISTS idx_product_preco ON product(preco);
