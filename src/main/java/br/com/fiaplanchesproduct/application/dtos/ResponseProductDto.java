package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;

import java.math.BigDecimal;

public record ResponseProductDto(Long id, String nomeProduto, BigDecimal preco, Category category, String urlImage) {

    public ResponseProductDto(Long id, String nomeProduto, BigDecimal preco, Category category) {
        this(id, nomeProduto, preco, category, null);
    }

    public static ResponseProductDto toResponseProductDto(ProductDto productDTO) {
        return new ResponseProductDto(
                productDTO.id(),
                productDTO.nomeProduto(),
                productDTO.preco(),
                productDTO.category(),
                productDTO.urlImage()
        );
    }

}