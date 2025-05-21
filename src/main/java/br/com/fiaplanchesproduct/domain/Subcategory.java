package br.com.fiaplanchesproduct.domain;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {
    private Long id;
    private String name;
    private Category category;

    public SubcategoryDto toSubcategoryDto() {
        return new SubcategoryDto(
                this.id,
                this.name,
                this.category
        );
    }
}
