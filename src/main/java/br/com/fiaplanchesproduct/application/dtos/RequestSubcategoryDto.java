package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestSubcategoryDto(
        @NotBlank(message = "Nome da subcategoria não pode ser vazio")
        String name,

        @NotNull(message = "Categoria não pode ser vazia")
        Category category
) {
    public SubcategoryDto toSubcategoryDto() {
        return new SubcategoryDto(
                null, // ID será gerado pelo banco
                this.name(),
                this.category()
        );
    }
}
