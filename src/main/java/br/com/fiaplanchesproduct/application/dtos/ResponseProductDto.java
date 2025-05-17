package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;

import java.math.BigDecimal;

public record ResponseProductDto(
        Long id, 
        String nomeProduto, 
        BigDecimal preco, 
        Category category, 
        String descricao,
        String urlImage,
        Integer estoque,
        boolean ativo
) {

    public ResponseProductDto(Long id, String nomeProduto, BigDecimal preco, Category category) {
        this(id, nomeProduto, preco, category, null, null, 0, true);
    }

    public static ResponseProductDto toResponseProductDto(ProductDto productDTO) {
        return new ResponseProductDto(
                productDTO.id(),
                productDTO.nomeProduto(),
                productDTO.preco(),
                productDTO.category(),
                productDTO.descricao(),
                productDTO.urlImage(),
                productDTO.estoque(),
                productDTO.ativo()
        );
    }
}