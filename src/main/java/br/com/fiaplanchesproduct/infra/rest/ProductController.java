package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.application.dtos.ResponseProductDto;
import br.com.fiaplanchesproduct.application.usecases.*;
import br.com.fiaplanchesproduct.domain.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
    private final ToggleProductStatusUseCase toggleProductStatusUseCase;
    private final FilterProductsUseCase filterProductsUseCase;

    public ProductController(
            RegisterProductUseCase registerProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            FindProductByCategoryUseCase findProductByCategoryUseCase,
            DeleteProductUseCase deleteProductUseCase,
            FindProductsByIdsUseCases findProductsByIdsUseCases,
            UploadStorageUseCase uploadStorageUseCase,
            DownloadStorageUseCase downloadStorageUseCase,
            FindProductsUseCase findProductsUseCase,
            ToggleProductStatusUseCase toggleProductStatusUseCase,
            FilterProductsUseCase filterProductsUseCase) {
        this.registerProductUseCase = registerProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.findProductByCategoryUseCase = findProductByCategoryUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.findProductsByIdsUseCases = findProductsByIdsUseCases;
        this.uploadStorageUseCase = uploadStorageUseCase;
        this.downloadStorageUseCase = downloadStorageUseCase;
        this.findProductsUseCase = findProductsUseCase;
        this.toggleProductStatusUseCase = toggleProductStatusUseCase;
        this.filterProductsUseCase = filterProductsUseCase;
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
    
    @GetMapping("/filter")
    public ResponseEntity<Page<ResponseProductDto>> filterProducts(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) BigDecimal precoMinimo,
            @RequestParam(required = false) BigDecimal precoMaximo,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<ProductDto> productDtoPage = filterProductsUseCase.filterProducts(
                category, precoMinimo, precoMaximo, ativo, page, size, sortBy, direction);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }
    
    @GetMapping("/filter/category")
    public ResponseEntity<Page<ResponseProductDto>> findProductsByCategoryPaged(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeProduto") String sortBy) {

        Page<ProductDto> productDtoPage = filterProductsUseCase.findProductsByCategoryPaged(
                category, page, size, sortBy);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }
    
    @GetMapping("/filter/price")
    public ResponseEntity<Page<ResponseProductDto>> findProductsByPriceRange(
            @RequestParam BigDecimal precoMinimo,
            @RequestParam BigDecimal precoMaximo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "preco") String sortBy) {

        Page<ProductDto> productDtoPage = filterProductsUseCase.findProductsByPriceRange(
                precoMinimo, precoMaximo, page, size, sortBy);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }
    
    @GetMapping("/filter/status")
    public ResponseEntity<Page<ResponseProductDto>> findProductsByStatus(
            @RequestParam boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDto> productDtoPage = filterProductsUseCase.findProductsByStatus(ativo, page, size, sortBy);
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

    @PatchMapping("/{id}/activate")
    public ResponseEntity<String> activateProduct(@PathVariable Long id) {
        toggleProductStatusUseCase.activateProduct(id);
        return ResponseEntity.ok("Produto ativado com sucesso!");
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id) {
        toggleProductStatusUseCase.deactivateProduct(id);
        return ResponseEntity.ok("Produto desativado com sucesso!");
    }
}
