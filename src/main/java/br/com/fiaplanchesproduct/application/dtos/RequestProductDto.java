package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestProductDto(
        @NotBlank(message = "Nome do produto não pode ser vazio")
        String nameProduct,

        @NotNull(message = "Preço não pode ser vazio")
        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que 0")
        BigDecimal price,

        @NotNull(message = "Categoria nao pode ser vazio")
        @Enumerated(EnumType.STRING)
        Category category, // Categoria principal

        Long subcategoryId, // ID da subcategoria

        String description,

        String imageUrl,

        @NotNull(message = "Estoque não pode ser vazio")
        @Min(value = 0, message = "Estoque deve ser maior ou igual a 0")
        Integer estoque,

        Boolean ativo,

        String sku,

        String barcode
) {
    public ProductDto toProductDto() {
        // A lógica para buscar a SubcategoryEntity pelo subcategoryId e converter para SubcategoryDto
        // precisará ser implementada no serviço/use case, pois o DTO não deve ter acesso a repositórios.
        // Por enquanto, passaremos null para subcategory no ProductDto e o serviço se encarregará de popular.
        SubcategoryDto subcategoryDto = null; 
        // Se subcategoryId for fornecido, o serviço deverá buscar a Subcategory e criar o SubcategoryDto.
        // Exemplo (a ser implementado no serviço):
        // if (this.subcategoryId != null) {
        //     Subcategory subcategory = subcategoryRepository.findById(this.subcategoryId).orElse(null);
        //     if (subcategory != null) {
        //         subcategoryDto = SubcategoryDto.fromSubcategory(subcategory);
        //     }
        // }

        return new ProductDto(
                null,
                this.nameProduct,
                this.price,
                this.category, // Categoria principal
                subcategoryDto, // Será preenchido pelo serviço
                this.description,
                null,
                this.imageUrl,
                this.estoque,
                this.ativo != null ? this.ativo : true,
                this.sku,
                this.barcode
        );
    }
}
