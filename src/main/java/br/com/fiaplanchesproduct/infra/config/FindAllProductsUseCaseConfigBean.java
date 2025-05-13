package br.com.fiaplanchesproduct.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.FindProductsUseCase;

@Configuration
public class FindAllProductsUseCaseConfigBean {

    @Bean
    public FindProductsUseCase findProductsUseCase(
            ProductRepositoryPortOut productRepositoryPortOut
        ) {
        return new FindProductsUseCase(productRepositoryPortOut);
    }
}
