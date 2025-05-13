package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
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
        Category category,
        String description,
        String imageUrl
) {
    public ProductDto toProductDto() {
        return new ProductDto(
                null,
                this.nameProduct,
                this.price,
                this.category,
                this.description,
                null,
                this.imageUrl
        );
    }
}
