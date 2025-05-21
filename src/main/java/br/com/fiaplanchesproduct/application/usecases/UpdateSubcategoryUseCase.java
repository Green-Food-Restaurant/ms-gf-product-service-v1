package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductNotFoundException;

public class UpdateSubcategoryUseCase {

    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;

    public UpdateSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
    }

    public SubcategoryDto updateSubcategory(Long id, SubcategoryDto subcategoryDto) {
        // Verifica se a subcategoria existe
        SubcategoryDto existingSubcategory = subcategoryRepositoryPortOut.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Subcategoria não encontrada com o ID: " + id));

        // Verifica se o nome e categoria estão sendo alterados para algo que já existe
        if (!existingSubcategory.name().equals(subcategoryDto.name()) || !existingSubcategory.category().equals(subcategoryDto.category())) {
            subcategoryRepositoryPortOut.findByNameAndCategory(subcategoryDto.name(), subcategoryDto.category())
                .ifPresent(foundSubcategory -> {
                    if (!foundSubcategory.id().equals(id)) { // Garante que não é a própria subcategoria
                        throw new ProductBusinessException("Subcategoria '" + subcategoryDto.name() + "' já existe para a categoria '" + subcategoryDto.category() + "'");
                    }
                });
        }

        // Atualiza os campos
        SubcategoryDto updatedSubcategory = new SubcategoryDto(id, subcategoryDto.name(), subcategoryDto.category());
        return subcategoryRepositoryPortOut.save(updatedSubcategory);
    }
}
