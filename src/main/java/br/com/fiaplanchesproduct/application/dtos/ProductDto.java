package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.Product;
import br.com.fiaplanchesproduct.domain.enums.Category;
import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String nomeProduto,
        BigDecimal preco,
        Category category, // Categoria principal
        SubcategoryDto subcategory,
        String descricao,
        String keyImage,
        String urlImage,
        Integer estoque,
        boolean ativo,
        String sku,
        String barcode
) {
    public ProductDto(
            Long id,
            String nomeProduto,
            BigDecimal preco,
            Category category, // Categoria principal
            SubcategoryDto subcategory,
            String descricao,
            String keyImage,
            String urlImage,
            Integer estoque,
            boolean ativo,
            String sku,
            String barcode
    ) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.category = category;
        this.subcategory = subcategory;
        this.descricao = descricao;
        this.keyImage = keyImage;
        this.urlImage = urlImage;
        this.estoque = estoque;
        this.ativo = ativo;
        this.sku = sku;
        this.barcode = barcode;
    }
    
    public static ProductDto toProductDto(Product produto) {
        return new ProductDto(
                produto.getId(),
                produto.getNomeProduto(),
                produto.getPreco(),
                produto.getCategory(), // Categoria principal
                produto.getSubcategory() != null ? SubcategoryDto.fromSubcategory(produto.getSubcategory()) : null,
                produto.getDescricao(),
                produto.getKeyImage(),
                produto.getUrlImage(),
                produto.getEstoque(),
                produto.isAtivo(),
                produto.getSku(),
                produto.getBarcode()
        );
    }

    public Product toProduct() {
        return new Product(
                this.id,
                this.nomeProduto,
                this.preco,
                this.category, // Categoria principal
                this.subcategory != null ? this.subcategory.toSubcategory() : null,
                this.descricao,
                this.keyImage,
                this.urlImage,
                this.estoque,
                this.ativo,
                this.sku,
                this.barcode
        );
    }
}
