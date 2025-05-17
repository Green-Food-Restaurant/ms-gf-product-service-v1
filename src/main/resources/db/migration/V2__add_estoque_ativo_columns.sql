-- Script para atualizar a tabela 'product' para adicionar as novas colunas
-- Se a coluna 'estoque' ainda não existir
ALTER TABLE product ADD COLUMN IF NOT EXISTS estoque INTEGER DEFAULT 0;

-- Se a coluna 'ativo' ainda não existir
ALTER TABLE product ADD COLUMN IF NOT EXISTS ativo BOOLEAN DEFAULT TRUE;

-- Atualiza o tamanho da coluna 'url_image' para 1000 caracteres
ALTER TABLE product ALTER COLUMN url_image TYPE VARCHAR(1000);

-- Opcionalmente, você pode adicionar uma restrição de verificação para garantir que o estoque não seja negativo
ALTER TABLE product ADD CONSTRAINT check_estoque_positive CHECK (estoque >= 0);
