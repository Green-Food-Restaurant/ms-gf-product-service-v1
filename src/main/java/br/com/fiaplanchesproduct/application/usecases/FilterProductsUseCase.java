package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

public class FilterProductsUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public FilterProductsUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public Page<ProductDto> filterProducts(
            Category category,
            Long subcategoryId,
            BigDecimal precoMinimo,
            BigDecimal precoMaximo,
            Boolean ativo,
            int page,
            int size,
            String sortBy,
            String direction) {
        
        Sort sort = direction.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return productRepositoryPortOut.findProductsByFilters(
                category, subcategoryId, precoMinimo, precoMaximo, ativo, pageable);
    }
    
    public Page<ProductDto> findProductsByPriceRange(
            BigDecimal precoMinimo, 
            BigDecimal precoMaximo, 
            int page, 
            int size, 
            String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        
        return productRepositoryPortOut.findProductsByPriceRange(
                precoMinimo, precoMaximo, pageable);
    }
    
    public Page<ProductDto> findProductsByStatus(
            boolean ativo, 
            int page, 
            int size, 
            String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        
        return productRepositoryPortOut.findProductsByStatus(ativo, pageable);
    }
    
    public Page<ProductDto> findProductsByCategoryPaged(
            Category category, 
            int page, 
            int size, 
            String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        
        return productRepositoryPortOut.findProductByCategoryPaged(category, pageable);
    }
}
