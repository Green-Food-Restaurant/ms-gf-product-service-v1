package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.RegisterProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterProductConfig {

    @Bean
    public RegisterProductUseCase registerProductUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new RegisterProductUseCase(productRepositoryPortOut);
    }
}
