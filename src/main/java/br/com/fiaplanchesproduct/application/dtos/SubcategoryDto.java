package br.com.fiaplanchesproduct.application.dtos;

import br.com.fiaplanchesproduct.domain.Subcategory;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.entity.SubcategoryEntity;

public record SubcategoryDto(
        Long id,
        String name,
        Category category
) {
    public static SubcategoryDto fromSubcategory(Subcategory subcategory) {
        return new SubcategoryDto(
                subcategory.getId(),
                subcategory.getName(),
                subcategory.getCategory()
        );
    }

    public Subcategory toSubcategory() {
        return new Subcategory(
                this.id(),
                this.name(),
                this.category()
        );
    }

    public static SubcategoryDto fromSubcategoryEntity(SubcategoryEntity subcategoryEntity) {
        return new SubcategoryDto(
                subcategoryEntity.getId(),
                subcategoryEntity.getName(),
                subcategoryEntity.getCategory()
        );
    }

    public SubcategoryEntity toSubcategoryEntity() {
        SubcategoryEntity entity = new SubcategoryEntity();
        entity.setId(this.id());
        entity.setName(this.name());
        entity.setCategory(this.category());
        return entity;
    }
}
