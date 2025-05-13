package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;

public class UpdateProductUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public UpdateProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public ProductDto updateProduct(ProductDto novoProdutoDTO, Long id) {
        ProductDto productDto = productRepositoryPortOut.findProductById(id).orElseThrow(
                () -> new ProductBusinessException("Produto não encontrado")
        );
        var product = productDto.toProduct();
        var productUpdate = product.updateProduct(novoProdutoDTO.toProduct());
        return productRepositoryPortOut.saveProduct(ProductDto.toProductDto(productUpdate));
    }
}
