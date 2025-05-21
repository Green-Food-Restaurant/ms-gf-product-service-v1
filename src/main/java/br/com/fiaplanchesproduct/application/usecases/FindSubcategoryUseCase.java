package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductNotFoundException;

import java.util.List;

public class FindSubcategoryUseCase {

    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;

    public FindSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
    }

    public SubcategoryDto findById(Long id) {
        return subcategoryRepositoryPortOut.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Subcategoria não encontrada com o ID: " + id));
    }

    public List<SubcategoryDto> findAll() {
        return subcategoryRepositoryPortOut.findAll()
                .orElseThrow(() -> new ProductNotFoundException("Nenhuma subcategoria encontrada"));
    }

    public List<SubcategoryDto> findByCategory(Category category) {
        return subcategoryRepositoryPortOut.findByCategory(category)
                .orElseThrow(() -> new ProductNotFoundException("Nenhuma subcategoria encontrada para a categoria: " + category));
    }
}
