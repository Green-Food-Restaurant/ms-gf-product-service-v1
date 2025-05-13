package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.ProductStoragePortOut;
import br.com.fiaplanchesproduct.application.usecases.UploadStorageUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadUseCaseConfigBean {

     @Bean
     public UploadStorageUseCase uploadStorageUseCase(ProductStoragePortOut productStoragePortOut, ProductRepositoryPortOut productRepositoryPortOut) {
         return new UploadStorageUseCase(productStoragePortOut, productRepositoryPortOut);
     }
}
