package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.ResponseProductDto;
import br.com.fiaplanchesproduct.application.usecases.FilterProductsUseCase;
import br.com.fiaplanchesproduct.application.usecases.FindProductByCategoryUseCase;
import br.com.fiaplanchesproduct.domain.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("v1/product/filter")
@Tag(name = "Filtros", description = "Operações de filtro e pesquisa de produtos")
public class ProductFilterController {

    private final FilterProductsUseCase filterProductsUseCase;
    private final FindProductByCategoryUseCase findProductByCategoryUseCase;

    public ProductFilterController(
            FilterProductsUseCase filterProductsUseCase,
            FindProductByCategoryUseCase findProductByCategoryUseCase) {
        this.filterProductsUseCase = filterProductsUseCase;
        this.findProductByCategoryUseCase = findProductByCategoryUseCase;
    }

    @GetMapping
    @Operation(summary = "Filtro avançado de produtos", description = "Filtra produtos com múltiplos critérios: categoria, subcategoria, preço e status")
    public ResponseEntity<Page<ResponseProductDto>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long subcategoryId, // Adicionado subcategoryId
            @RequestParam(required = false) BigDecimal precoMinimo,
            @RequestParam(required = false) BigDecimal precoMaximo,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Category categoryEnum = null;
        if (category != null && !category.isEmpty()) {
            try {
                categoryEnum = Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Se a categoria não for válida, retornamos uma página vazia
                return ResponseEntity.ok(Page.empty());
            }
        }

        Page<ProductDto> productDtoPage = filterProductsUseCase.filterProducts(
                categoryEnum, subcategoryId, precoMinimo, precoMaximo, ativo, page, size, sortBy, direction);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }

    @GetMapping("/category")
    @Operation(summary = "Filtro por categoria com paginação", description = "Busca produtos por categoria com suporte a paginação e ordenação")
    public ResponseEntity<Page<ResponseProductDto>> findProductsByCategoryPaged(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeProduto") String sortBy) {

        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            Page<ProductDto> productDtoPage = filterProductsUseCase.findProductsByCategoryPaged(
                    categoryEnum, page, size, sortBy);
            Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
            return ResponseEntity.ok(responseProductDtoPage);
        } catch (IllegalArgumentException e) {
            // Se a categoria não for válida, retornamos uma página vazia
            return ResponseEntity.ok(Page.empty());
        }
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Busca simples por categoria", description = "Retorna todos os produtos de uma categoria específica")
    public ResponseEntity<List<ResponseProductDto>> findByCategory(@PathVariable String category) {
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            List<ProductDto> productDtos = findProductByCategoryUseCase.findProductByCategory(categoryEnum);
            return ResponseEntity.ok(productDtos.stream().map(ResponseProductDto::toResponseProductDto).toList());
        } catch (IllegalArgumentException e) {
            // Se a categoria não for válida, retornamos uma lista vazia
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping("/price")
    @Operation(summary = "Filtro por faixa de preço", description = "Busca produtos dentro de uma faixa de preço específica")
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

    @GetMapping("/status")
    @Operation(summary = "Filtro por status", description = "Busca produtos ativos ou inativos")
    public ResponseEntity<Page<ResponseProductDto>> findProductsByStatus(
            @RequestParam boolean ativo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDto> productDtoPage = filterProductsUseCase.findProductsByStatus(ativo, page, size, sortBy);
        Page<ResponseProductDto> responseProductDtoPage = productDtoPage.map(ResponseProductDto::toResponseProductDto);
        return ResponseEntity.ok(responseProductDtoPage);
    }
}
