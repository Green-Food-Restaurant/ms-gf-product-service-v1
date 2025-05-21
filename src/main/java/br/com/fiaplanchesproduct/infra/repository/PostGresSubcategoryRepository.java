package br.com.fiaplanchesproduct.infra.repository;

import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostGresSubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {

    List<SubcategoryEntity> findByCategory(Category category);

    Optional<SubcategoryEntity> findByNameAndCategory(String name, Category category);
}
