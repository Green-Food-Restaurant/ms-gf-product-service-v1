package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.FindProductByCategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindProductByCategoryConfig {

    @Bean
    public FindProductByCategoryUseCase findProductByCategoryUseCase(ProductRepositoryPortOut productRepositoryPortOut) {
        return new FindProductByCategoryUseCase(productRepositoryPortOut);
    }
}
