package br.com.fiaplanchesproduct.infra.adapters.out;

import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.PostGresSubcategoryRepository;
import br.com.fiaplanchesproduct.infra.repository.entity.SubcategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SubcategoryRepositoryAdapterOut implements SubcategoryRepositoryPortOut {

    private final PostGresSubcategoryRepository postGresSubcategoryRepository;

    public SubcategoryRepositoryAdapterOut(PostGresSubcategoryRepository postGresSubcategoryRepository) {
        this.postGresSubcategoryRepository = postGresSubcategoryRepository;
    }

    @Override
    public SubcategoryDto save(SubcategoryDto subcategoryDto) {
        SubcategoryEntity subcategoryEntity = subcategoryDto.toSubcategoryEntity();
        SubcategoryEntity savedEntity = postGresSubcategoryRepository.save(subcategoryEntity);
        return SubcategoryDto.fromSubcategoryEntity(savedEntity);
    }

    @Override
    public Optional<SubcategoryDto> findById(Long id) {
        return postGresSubcategoryRepository.findById(id)
                .map(SubcategoryDto::fromSubcategoryEntity);
    }

    @Override
    public Optional<List<SubcategoryDto>> findAll() {
        List<SubcategoryEntity> entities = postGresSubcategoryRepository.findAll();
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(entities.stream()
                .map(SubcategoryDto::fromSubcategoryEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<SubcategoryDto>> findByCategory(Category category) {
        List<SubcategoryEntity> entities = postGresSubcategoryRepository.findByCategory(category);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(entities.stream()
                .map(SubcategoryDto::fromSubcategoryEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteById(Long id) {
        postGresSubcategoryRepository.deleteById(id);
    }

    @Override
    public Optional<SubcategoryDto> findByNameAndCategory(String name, Category category) {
        return postGresSubcategoryRepository.findByNameAndCategory(name, category)
                .map(SubcategoryDto::fromSubcategoryEntity);
    }
}
