package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;

public class CreateSubcategoryUseCase {

    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;

    public CreateSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
    }

    public SubcategoryDto createSubcategory(SubcategoryDto subcategoryDto) {
        // Verifica se já existe uma subcategoria com o mesmo nome para a mesma categoria
        subcategoryRepositoryPortOut.findByNameAndCategory(subcategoryDto.name(), subcategoryDto.category())
            .ifPresent(existingSubcategory -> {
                throw new ProductBusinessException("Subcategoria '" + subcategoryDto.name() + "' já existe para a categoria '" + subcategoryDto.category() + "'");
            });
        return subcategoryRepositoryPortOut.save(subcategoryDto);
    }
}
