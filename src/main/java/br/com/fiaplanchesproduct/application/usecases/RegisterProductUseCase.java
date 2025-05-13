package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterProductUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public RegisterProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public ProductDto registerProduct(ProductDto productDto) {
        log.info("Start registerProduct");

        
        return productRepositoryPortOut.saveProduct(productDto);
    }
}
