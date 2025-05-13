package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.ProductStoragePortOut;
import br.com.fiaplanchesproduct.domain.Product;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class UploadStorageUseCase {

    private final ProductStoragePortOut productStoragePortOut;
    private final ProductRepositoryPortOut productRepositoryPortOut;

    public UploadStorageUseCase(ProductStoragePortOut productStoragePortOut, ProductRepositoryPortOut productRepositoryPortOut) {
        this.productStoragePortOut = productStoragePortOut;
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public void upload(Long id, @NotNull MultipartFile file) {
        log.info("Start metodo upload");

        validateFile(file);

        ProductDto productDto = productRepositoryPortOut.findProductById(id).orElseThrow(
                () -> new ProductBusinessException("Product not found")
        );

        if (productDto.urlImage() != null) {
            productStoragePortOut.delete(productDto.keyImage());
        }

        // Armazena o arquivo e obtém a chave gerada
        String key = productStoragePortOut.store(file);

        String urlImage = productStoragePortOut.generatePresignedUrlImage(key);

        // Atualiza o ProductDto com a nova chave
        Product product = productDto.toProduct();
        product.setKeyImage(key);
        product.setUrlImage(urlImage);
        productDto = ProductDto.toProductDto(product);

        // Salva o ProductDto atualizado no banco de dados
        productRepositoryPortOut.saveProduct(productDto);

        log.info("End metodo upload");
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new IllegalArgumentException("File size exceeds limit");
        }
    }
}
