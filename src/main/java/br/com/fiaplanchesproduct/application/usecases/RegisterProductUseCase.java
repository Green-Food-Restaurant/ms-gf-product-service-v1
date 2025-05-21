package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.Subcategory;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterProductUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;
    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;

    public RegisterProductUseCase(ProductRepositoryPortOut productRepositoryPortOut, SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
    }

    public ProductDto registerProduct(RequestProductDto requestProductDto) {
        log.info("Start registerProduct");

        Subcategory subcategory = null;
        SubcategoryDto subcategoryDto = null;
        if (requestProductDto.subcategoryId() != null) {
            subcategoryDto = subcategoryRepositoryPortOut.findById(requestProductDto.subcategoryId())
                    .orElseThrow(() -> new ProductBusinessException("Subcategoria não encontrada com o ID: " + requestProductDto.subcategoryId()));
            
            subcategory = subcategoryDto.toSubcategory();
            if (requestProductDto.category() != null && !requestProductDto.category().equals(subcategory.getCategory())) {
                throw new ProductBusinessException("A categoria do produto (" + requestProductDto.category() +
                        ") não corresponde à categoria da subcategoria (" + subcategory.getCategory() + ")");
            }
        }

        ProductDto productDtoToSave = requestProductDto.toProductDto();

        return productRepositoryPortOut.saveProduct(productDtoToSave);
    }
}
