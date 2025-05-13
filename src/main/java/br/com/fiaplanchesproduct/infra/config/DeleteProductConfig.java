package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.DeleteProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteProductConfig {


    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new DeleteProductUseCase(productRepositoryPortOut);
    }
}
