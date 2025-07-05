package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.usecases.DownloadStorageUseCase;
import br.com.fiaplanchesproduct.application.usecases.UploadStorageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v1/product")
@Tag(name = "Imagens", description = "Operações relacionadas a imagens de produtos")
public class ProductImageController {

    private final UploadStorageUseCase uploadStorageUseCase;
    private final DownloadStorageUseCase downloadStorageUseCase;

    public ProductImageController(
            UploadStorageUseCase uploadStorageUseCase,
            DownloadStorageUseCase downloadStorageUseCase) {
        this.uploadStorageUseCase = uploadStorageUseCase;
        this.downloadStorageUseCase = downloadStorageUseCase;
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload de imagem", description = "Faz upload de uma imagem para um produto específico")
    public ResponseEntity<Void> uploadImage(
            @PathVariable Long id,
            @RequestParam MultipartFile file) {

        uploadStorageUseCase.upload(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    @Operation(summary = "Download de imagem", description = "Obtém a URL da imagem de um produto específico")
    public ResponseEntity<String> downloadImage(@PathVariable Long id) {
        String imageUrl = downloadStorageUseCase.download(id);
        return ResponseEntity.ok(imageUrl);
    }
}
