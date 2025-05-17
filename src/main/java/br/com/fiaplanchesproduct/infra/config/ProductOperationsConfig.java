package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.FilterProductsUseCase;
import br.com.fiaplanchesproduct.application.usecases.ToggleProductStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductOperationsConfig {

    @Bean
    public ToggleProductStatusUseCase toggleProductStatusUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new ToggleProductStatusUseCase(productRepositoryPortOut);
    }

    @Bean
    public FilterProductsUseCase filterProductsUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new FilterProductsUseCase(productRepositoryPortOut);
    }
}
