package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Slf4j
public class FindProductsUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public FindProductsUseCase(
            ProductRepositoryPortOut productRepositoryPortOut
        ) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public Page<ProductDto> findProducts(int page, int size, String sortBy) {
        log.info("Starting findProducts method with page: {}, size: {}, sortBy: {}", page, size, sortBy);
        String sanitizedSortBy = sortBy.trim().toLowerCase();

        String validSortField = validateAndMapSortField(sanitizedSortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(validSortField));
        Page<ProductDto> products = productRepositoryPortOut.findAllProducts(pageable);
        
        log.info("Found {} products", products.getTotalElements());
        return products;
    }

    private String validateAndMapSortField(String sortField) {
        // Lista de campos permitidos para ordenação
        List<String> allowedFields = Arrays.asList("id", "nomeProduto", "preco", "category");
        
        // Se o campo estiver na lista de permitidos, use-o, caso contrário, use "id" como padrão
        if (allowedFields.contains(sortField.toLowerCase())) {
            return sortField.toLowerCase();
        }
        
        log.warn("Invalid sort field: {}. Using default sort field: id", sortField);
        return "id";
    }
}