package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;

import java.util.List;

public class FindProductByCategoryUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;
    public FindProductByCategoryUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public List<ProductDto> findProductByCategory(Category category) {
        return productRepositoryPortOut.findProductByCategory(category).orElseThrow(
                () -> new ProductBusinessException("Nenhum produto encontrado")
        );
    }
}
