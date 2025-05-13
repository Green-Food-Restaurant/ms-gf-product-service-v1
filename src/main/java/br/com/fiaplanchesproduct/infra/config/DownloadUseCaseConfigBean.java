package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.ProductStoragePortOut;
import br.com.fiaplanchesproduct.application.usecases.DownloadStorageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadUseCaseConfigBean {

     @Bean
     public DownloadStorageUseCase downloadStorageUseCase(ProductStoragePortOut productStoragePortOut, ProductRepositoryPortOut productRepositoryPortOut) {
         return new DownloadStorageUseCase(productStoragePortOut, productRepositoryPortOut);
     }
}
