package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.ProductStoragePortOut;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DownloadStorageUseCase {

    private final ProductStoragePortOut productStoragePortOut;
    private final ProductRepositoryPortOut productRepositoryPortOut;

    public DownloadStorageUseCase(ProductStoragePortOut productStoragePortOut, ProductRepositoryPortOut productRepositoryPortOut) {
        this.productStoragePortOut = productStoragePortOut;
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public String download(Long productId) {
        log.info("Start method download");

        log.info("Product ID: {}", productId);
        ProductDto productDto = productRepositoryPortOut.findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        log.info("Product found: {}", productDto);

        validateKey(productDto.urlImage());

        String file = productStoragePortOut.generatePresignedUrlImage(productDto.urlImage());

        if (file == null) {
            throw new IllegalArgumentException("File not found");
        }

        log.info("End method download");
        return file;
    }

    private void validateKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key is empty");
        }

        if (key.length() > 255) { // 255 characters
            throw new IllegalArgumentException("Key exceeds limit");
        }
    }
}
