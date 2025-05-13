package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.FindProductsByIdsUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindProductsByIdsConfig {

    @Bean
    public FindProductsByIdsUseCases findProductsByIdsUseCases(ProductRepositoryPortOut productRepositoryPortOut) {
        return new FindProductsByIdsUseCases(productRepositoryPortOut);
    }
}
