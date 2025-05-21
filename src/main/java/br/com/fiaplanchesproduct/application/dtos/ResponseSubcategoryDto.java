package br.com.fiaplanchesproduct.application.dtos;

public record ResponseSubcategoryDto(
        Long id,
        String name
) {
    public static ResponseSubcategoryDto fromSubcategoryDto(SubcategoryDto subcategoryDto) {
        return new ResponseSubcategoryDto(
                subcategoryDto.id(),
                subcategoryDto.name()
        );
    }
}
