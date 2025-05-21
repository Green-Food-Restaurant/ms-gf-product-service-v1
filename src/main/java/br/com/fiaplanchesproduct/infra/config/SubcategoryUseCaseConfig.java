package br.com.fiaplanchesproduct.infra.config;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.application.usecases.CreateSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.DeleteSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.FindSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.UpdateSubcategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubcategoryUseCaseConfig {

    @Bean
    public CreateSubcategoryUseCase createSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        return new CreateSubcategoryUseCase(subcategoryRepositoryPortOut);
    }

    @Bean
    public FindSubcategoryUseCase findSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        return new FindSubcategoryUseCase(subcategoryRepositoryPortOut);
    }

    @Bean
    public UpdateSubcategoryUseCase updateSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        return new UpdateSubcategoryUseCase(subcategoryRepositoryPortOut);
    }

    @Bean
    public DeleteSubcategoryUseCase deleteSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut, ProductRepositoryPortOut productRepositoryPortOut) {
        return new DeleteSubcategoryUseCase(subcategoryRepositoryPortOut, productRepositoryPortOut);
    }
}
