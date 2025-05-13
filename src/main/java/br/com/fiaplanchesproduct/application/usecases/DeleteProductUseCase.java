package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;

public class DeleteProductUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public DeleteProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public void deleteProduct(Long id) {
        ProductDto productDto = productRepositoryPortOut.findProductById(id).orElseThrow(
                () -> new ProductBusinessException("Produto não encontrado")
        );
        productRepositoryPortOut.deleteProduct(productDto);
    }
}
