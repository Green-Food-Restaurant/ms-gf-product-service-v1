package br.com.fiaplanchesproduct.infra.repository;

import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PostGresProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    List<ProductEntity> findByCategory(Category category);
    
    Page<ProductEntity> findByCategoryAndAtivoTrue(Category category, Pageable pageable);
    
    Page<ProductEntity> findByPrecoGreaterThanEqualAndPrecoLessThanEqualAndAtivoTrue(
            BigDecimal precoMinimo, BigDecimal precoMaximo, Pageable pageable);
    
    Page<ProductEntity> findByAtivo(boolean ativo, Pageable pageable);
    
    @Query("SELECT p FROM ProductEntity p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:precoMinimo IS NULL OR p.preco >= :precoMinimo) AND " +
           "(:precoMaximo IS NULL OR p.preco <= :precoMaximo) AND " +
           "(:ativo IS NULL OR p.ativo = :ativo)")
    Page<ProductEntity> findByFilters(
            @Param("category") Category category,
            @Param("precoMinimo") BigDecimal precoMinimo,
            @Param("precoMaximo") BigDecimal precoMaximo,
            @Param("ativo") Boolean ativo,
            Pageable pageable);
    
    Page<ProductEntity> findAll(Pageable pageable);
}
