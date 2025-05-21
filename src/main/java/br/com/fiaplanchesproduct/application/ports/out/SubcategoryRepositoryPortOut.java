package br.com.fiaplanchesproduct.application.ports.out;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.domain.enums.Category;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepositoryPortOut {

    SubcategoryDto save(SubcategoryDto subcategoryDto);

    Optional<SubcategoryDto> findById(Long id);

    Optional<List<SubcategoryDto>> findAll();

    Optional<List<SubcategoryDto>> findByCategory(Category category);

    void deleteById(Long id);

    Optional<SubcategoryDto> findByNameAndCategory(String name, Category category);
}
