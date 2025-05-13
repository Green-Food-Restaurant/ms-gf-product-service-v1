package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.UpdateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateProductConfig {

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new UpdateProductUseCase(productRepositoryPortOut);
    }
}
