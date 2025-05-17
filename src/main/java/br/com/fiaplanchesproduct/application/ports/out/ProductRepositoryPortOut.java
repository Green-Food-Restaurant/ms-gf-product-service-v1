package br.com.fiaplanchesproduct.application.ports.out;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.domain.enums.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for the Product Repository Port Out.
 * This interface defines the methods that the application layer can use to interact with the product repository.
 */
public interface ProductRepositoryPortOut {

    Optional<List<ProductDto>> findProductByCategory(Category category);
    
    Page<ProductDto> findProductByCategoryPaged(Category category, Pageable pageable);

    ProductDto saveProduct(ProductDto productDto);

    void deleteProduct(ProductDto productDto);
    
    void toggleProductStatus(Long id, boolean active);

    Optional<ProductDto> findProductById(Long id);

    Optional<List<ProductDto>> findProductsByIds(List<Long> ids);

    Page<ProductDto> findAllProducts(Pageable pageable);
    
    Page<ProductDto> findProductsByStatus(boolean ativo, Pageable pageable);
    
    Page<ProductDto> findProductsByPriceRange(BigDecimal precoMinimo, BigDecimal precoMaximo, Pageable pageable);
    
    Page<ProductDto> findProductsByFilters(
            Category category, 
            BigDecimal precoMinimo, 
            BigDecimal precoMaximo, 
            Boolean ativo, 
            Pageable pageable);
}
