package br.com.fiaplanchesproduct.application.ports.out;

import org.springframework.web.multipart.MultipartFile;

public interface ProductStoragePortOut {

    String store(MultipartFile file);

    String generatePresignedUrlImage(String key);

    void delete(String key);
}
