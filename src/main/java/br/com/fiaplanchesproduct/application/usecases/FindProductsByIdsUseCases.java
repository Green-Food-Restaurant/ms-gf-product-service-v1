package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;

@Slf4j
public class FindProductsByIdsUseCases {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public FindProductsByIdsUseCases(ProductRepositoryPortOut productRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public List<ProductDto> findProductsByIds(List<Long> productIds) {
        log.info("Executando findProductsByIds");

        List<ProductDto> products = productRepositoryPortOut.findProductsByIds(productIds)
                .orElseThrow(
                        () -> new ProductBusinessException("Products not found")
                );

        var idsMatch = new HashSet<>(products.stream().map(ProductDto::id).toList()).containsAll(productIds);

        log.info("Validando ids dos produtos passados na request com os produtos cadastrados, retorno: {}", idsMatch);
        if (!idsMatch) {
            productIds.removeAll(products.stream().map(ProductDto::id).toList());
            throw new ProductBusinessException("Product not found, ID: " + productIds);
        }

        log.info("Retorno produtos: {}", products);
        return products;
    }
}
