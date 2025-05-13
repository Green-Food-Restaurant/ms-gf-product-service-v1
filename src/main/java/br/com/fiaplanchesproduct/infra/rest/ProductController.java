package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.application.dtos.ResponseProductDto;
import br.com.fiaplanchesproduct.application.usecases.*;
import br.com.fiaplanchesproduct.domain.enums.Category;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/product")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;

    private final UpdateProductUseCase updateProductUseCase;

    private final FindProductByCategoryUseCase findProductByCategoryUseCase;

    private final DeleteProductUseCase deleteProductUseCase;

    private final FindProductsByIdsUseCases findProductsByIdsUseCases;

    private final UploadStorageUseCase uploadStorageUseCase;

    private final DownloadStorageUseCase downloadStorageUseCase;

    private final FindProductsUseCase findProductsUseCase;

    public ProductController(
            RegisterProductUseCase registerProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            FindProductByCategoryUseCase findProductByCategoryUseCase,
            DeleteProductUseCase deleteProductUseCase,
            FindProductsByIdsUseCases findProductsByIdsUseCases,
            UploadStorageUseCase uploadStorageUseCase,
            DownloadStorageUseCase downloadStorageUseCase,
            FindProductsUseCase findProductsUseCase) {
        this.registerProductUseCase = registerProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.findProductByCategoryUseCase = findProductByCategoryUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.findProductsByIdsUseCases = findProductsByIdsUseCases;
        this.uploadStorageUseCase = uploadStorageUseCase;
        this.downloadStorageUseCase = downloadStorageUseCase;
        this.findProductsUseCase = findProductsUseCase;
    }

    @GetMapping("/find/{category}")
    public ResponseEntity<List<ResponseProductDto>> findByCategory(@PathVariable Category category) {
        List<ProductDto> productDtos = findProductByCategoryUseCase.findProductByCategory(category);
        return ResponseEntity.ok(productDtos.stream().map(ResponseProductDto::toResponseProductDto).toList());
    }

    @GetMapping("/find/ids")
    public ResponseEntity<List<ResponseProductDto>> findProductsByIds(@RequestParam @Valid List<Long> ids) {
        List<ProductDto> productDtos = findProductsByIdsUseCases.findProductsByIds(ids);
        return ResponseEntity.ok(productDtos.stream().map(ResponseProductDto::toResponseProductDto).toList());
    }

    @GetMapping("/find")
    public ResponseEntity<Page<ResponseProductDto>> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDto> productDtoPage = findProductsUseCase.findProducts(page, size, sortBy);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseProductDto> registerProduct(@RequestBody @Valid RequestProductDto requestProductDto) {
        ProductDto productDto = registerProductUseCase.registerProduct(requestProductDto.toProductDto());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseProductDto.toResponseProductDto(productDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@RequestBody @Valid RequestProductDto requestProductDto, @PathVariable Long id) {
        ProductDto productDto = updateProductUseCase.updateProduct(requestProductDto.toProductDto(), id);
        return ResponseEntity.ok(ResponseProductDto.toResponseProductDto(productDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeProduto(@PathVariable @Valid Long id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.ok("Produto removido com sucesso!");
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<ProductDto> upload(
            @PathVariable Long id,
            @RequestParam MultipartFile file) {

        uploadStorageUseCase.upload(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<String> download(@PathVariable Long id) {
        String data = downloadStorageUseCase.download(id);
        return ResponseEntity.ok()  // ou derive dinamicamente
                .body(data);
    }
}
