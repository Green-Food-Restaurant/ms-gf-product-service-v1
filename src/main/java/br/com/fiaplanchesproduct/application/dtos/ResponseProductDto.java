package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;

import java.math.BigDecimal;

public record ResponseProductDto(
        Long id, 
        String nomeProduto, 
        BigDecimal preco, 
        Category category, // Categoria principal
        ResponseSubcategoryDto subcategory,
        String descricao,
        String urlImage,
        Integer estoque,
        boolean ativo,
        String sku,
        String barcode
) {

    public ResponseProductDto(Long id, String nomeProduto, BigDecimal preco, Category category) {
        this(id, nomeProduto, preco, category, null, null, null, 0, true, null, null);
    }

    public static ResponseProductDto toResponseProductDto(ProductDto productDTO) {
        return new ResponseProductDto(
                productDTO.id(),
                productDTO.nomeProduto(),
                productDTO.preco(),
                productDTO.category(), // Categoria principal
                productDTO.subcategory() != null ? ResponseSubcategoryDto.fromSubcategoryDto(productDTO.subcategory()) : null,
                productDTO.descricao(),
                productDTO.urlImage(),
                productDTO.estoque(),
                productDTO.ativo(),
                productDTO.sku(),
                productDTO.barcode()
        );
    }
}