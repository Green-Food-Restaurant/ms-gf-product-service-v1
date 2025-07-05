package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.application.dtos.ResponseProductDto;
import br.com.fiaplanchesproduct.application.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/product")
@Tag(name = "Produtos", description = "Operações básicas de CRUD para gerenciamento de produtos")
public class ProductCrudController {

    private final RegisterProductUseCase registerProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final FindProductsUseCase findProductsUseCase;
    private final FindProductsByIdsUseCases findProductsByIdsUseCases;
    private final ToggleProductStatusUseCase toggleProductStatusUseCase;

    public ProductCrudController(
            RegisterProductUseCase registerProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase,
            FindProductsUseCase findProductsUseCase,
            FindProductsByIdsUseCases findProductsByIdsUseCases,
            ToggleProductStatusUseCase toggleProductStatusUseCase) {
        this.registerProductUseCase = registerProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.findProductsUseCase = findProductsUseCase;
        this.findProductsByIdsUseCases = findProductsByIdsUseCases;
        this.toggleProductStatusUseCase = toggleProductStatusUseCase;
    }

    @GetMapping("/find")
    @Operation(summary = "Listar produtos com paginação", description = "Retorna uma lista paginada de produtos com suporte a ordenação")
    public ResponseEntity<Page<ResponseProductDto>> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDto> productDtoPage = findProductsUseCase.findProducts(page, size, sortBy);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }

    @GetMapping("/find/ids")
    @Operation(summary = "Buscar produtos por IDs", description = "Retorna uma lista de produtos a partir de seus IDs")
    public ResponseEntity<List<ResponseProductDto>> findProductsByIds(@RequestParam @Valid List<Long> ids) {
        List<ProductDto> productDtos = findProductsByIdsUseCases.findProductsByIds(ids);
        return ResponseEntity.ok(productDtos.stream().map(ResponseProductDto::toResponseProductDto).toList());
    }

    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto com todas as informações")
    public ResponseEntity<ResponseProductDto> registerProduct(@RequestBody @Valid RequestProductDto requestProductDto) {
        ProductDto productDto = registerProductUseCase.registerProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseProductDto.toResponseProductDto(productDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Modifica as informações de um produto existente")
    public ResponseEntity<ResponseProductDto> updateProduct(@RequestBody @Valid RequestProductDto requestProductDto, @PathVariable Long id) {
        ProductDto productDto = updateProductUseCase.updateProduct(requestProductDto, id);
        return ResponseEntity.ok(ResponseProductDto.toResponseProductDto(productDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto do catálogo pelo seu ID")
    public ResponseEntity<String> removeProduct(@PathVariable @Valid Long id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.ok("Produto removido com sucesso!");
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Ativar produto", description = "Ativa um produto que estava desativado")
    public ResponseEntity<ResponseProductDto> activateProduct(@PathVariable Long id) {
        ProductDto updatedProductDto = toggleProductStatusUseCase.activateProduct(id);
        return ResponseEntity.ok(ResponseProductDto.toResponseProductDto(updatedProductDto));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desativar produto", description = "Desativa um produto sem excluí-lo do catálogo")
    public ResponseEntity<ResponseProductDto> deactivateProduct(@PathVariable Long id) {
        ProductDto updatedProductDto = toggleProductStatusUseCase.deactivateProduct(id);
        return ResponseEntity.ok(ResponseProductDto.toResponseProductDto(updatedProductDto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status do produto", description = "Ativa ou desativa um produto baseado no valor boolean fornecido")
    public ResponseEntity<ResponseProductDto> setProductStatus(
            @PathVariable Long id, 
            @RequestParam Boolean active) {
        ProductDto updatedProductDto = toggleProductStatusUseCase.toggleProductStatus(id, active);
        return ResponseEntity.ok(ResponseProductDto.toResponseProductDto(updatedProductDto));
    }
}
