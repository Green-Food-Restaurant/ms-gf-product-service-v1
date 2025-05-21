package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.Product;
import br.com.fiaplanchesproduct.domain.Subcategory;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;

public class UpdateProductUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;
    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;

    public UpdateProductUseCase(ProductRepositoryPortOut productRepositoryPortOut, SubcategoryRepositoryPortOut subcategoryRepositoryPortOut) {
        this.productRepositoryPortOut = productRepositoryPortOut;
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
    }

    public ProductDto updateProduct(RequestProductDto requestProductDto, Long id) {
        ProductDto existingProductDto = productRepositoryPortOut.findProductById(id).orElseThrow(
                () -> new ProductBusinessException("Produto não encontrado com o ID: " + id)
        );

        SubcategoryDto subcategoryDomainDto = null;
        Subcategory subcategoryDomain = null;
        if (requestProductDto.subcategoryId() != null) {
            subcategoryDomainDto = subcategoryRepositoryPortOut.findById(requestProductDto.subcategoryId())
                    .orElseThrow(() -> new ProductBusinessException("Subcategoria não encontrada com o ID: " + requestProductDto.subcategoryId()));
            subcategoryDomain = subcategoryDomainDto.toSubcategory();

            if (requestProductDto.category() != null && !requestProductDto.category().equals(subcategoryDomain.getCategory())) {
                throw new ProductBusinessException("A categoria do produto (" + requestProductDto.category() +
                        ") não corresponde à categoria da subcategoria (" + subcategoryDomain.getCategory() + ")");
            }
        } else {
            subcategoryDomain = null; // Garante que o productDomain também não terá subcategoria
        }
        

        Product productToUpdate = existingProductDto.toProduct();
        
        // Cria um Product domain a partir do request DTO para usar no método de update
        // Este objeto representa os campos que o usuário deseja atualizar.
        Product fieldsToUpdateFromRequest = new Product(
            null, // ID não é relevante para o objeto de atualização de campos
            requestProductDto.nameProduct(),
            requestProductDto.price(),
            requestProductDto.category(), // Categoria principal do request
            subcategoryDomain, // Subcategoria do domínio (pode ser null se removendo/não informada)
            requestProductDto.description(),
            null, // keyImage não vem do request direto, gerenciado em outro fluxo
            null, // urlImage não vem do request direto, gerenciado em outro fluxo
            requestProductDto.estoque(), // Estoque do request
            requestProductDto.ativo() != null ? requestProductDto.ativo() : productToUpdate.isAtivo(), // Ativo do request ou mantém o existente
            requestProductDto.sku(),
            requestProductDto.barcode()
        );

        // O método updateProduct em Product.java precisa ser ajustado para lidar com subcategory null
        productToUpdate.updateProduct(fieldsToUpdateFromRequest);
        
        // Após productToUpdate.updateProduct(), productToUpdate agora tem os campos atualizados,
        // incluindo a subcategoria (que pode ser null se fieldsToUpdateFromRequest.getSubcategory() for null).
        // Agora, converta productToUpdate (que é um objeto de domínio Product) para ProductDto.
        // O subcategoryDtoForUpdate já foi determinado e reflete o estado desejado da subcategoria.

        ProductDto productDtoToSave = productToUpdate.toProductDto();

        return productRepositoryPortOut.saveProduct(productDtoToSave);
    }
}
