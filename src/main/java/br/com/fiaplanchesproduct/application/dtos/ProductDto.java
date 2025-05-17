package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.Product;
import br.com.fiaplanchesproduct.domain.enums.Category;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String nomeProduto,
        BigDecimal preco,
        Category category,
        String descricao,
        String keyImage,
        String urlImage,
        Integer estoque,
        boolean ativo
) {    public static ProductDto toProductDto(Product produto) {
        return new ProductDto(
                produto.getId(),
                produto.getNomeProduto(),
                produto.getPreco(),
                produto.getCategory(),
                produto.getDescricao(),
                produto.getKeyImage(),
                produto.getUrlImage(),
                produto.getEstoque(),
                produto.isAtivo()
        );
    }

    public Product toProduct() {
        return new Product(
                this.id,
                this.nomeProduto,
                this.preco,
                this.category,
                this.descricao,
                this.keyImage,
                this.urlImage,
                this.estoque,
                this.ativo
        );
    }
}
