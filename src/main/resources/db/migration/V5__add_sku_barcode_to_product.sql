ALTER TABLE product ADD COLUMN sku VARCHAR(255);
ALTER TABLE product ADD COLUMN barcode VARCHAR(255);

UPDATE product SET sku = 'SKU_LANCHE_001', barcode = '1234567890123' WHERE nome_produto = 'Hambúrguer Clássico';
UPDATE product SET sku = 'SKU_LANCHE_002', barcode = '1234567890124' WHERE nome_produto = 'X-Salada Especial';
UPDATE product SET sku = 'SKU_ACOMP_001', barcode = '1234567890125' WHERE nome_produto = 'Batata Frita';
UPDATE product SET sku = 'SKU_BEBIDA_001', barcode = '1234567890126' WHERE nome_produto = 'Refrigerante';
UPDATE product SET sku = 'SKU_SOBREM_001', barcode = '1234567890127' WHERE nome_produto = 'Sorvete';
UPDATE product SET sku = 'SKU_SALADA_001', barcode = '1234567890128' WHERE nome_produto = 'Salada Caesar';
UPDATE product SET sku = 'SKU_SALADA_002', barcode = '1234567890129' WHERE nome_produto = 'Salada de Frutas';
