package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.RequestSubcategoryDto;
import br.com.fiaplanchesproduct.application.dtos.ResponseSubcategoryDto;
import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.application.usecases.CreateSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.DeleteSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.FindSubcategoryUseCase;
import br.com.fiaplanchesproduct.application.usecases.UpdateSubcategoryUseCase;
import br.com.fiaplanchesproduct.domain.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/subcategories")
@Tag(name = "Subcategorias", description = "Endpoints para gerenciamento de subcategorias de produtos")
public class SubcategoryController {

    private final CreateSubcategoryUseCase createSubcategoryUseCase;
    private final FindSubcategoryUseCase findSubcategoryUseCase;
    private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
    private final DeleteSubcategoryUseCase deleteSubcategoryUseCase;

    public SubcategoryController(CreateSubcategoryUseCase createSubcategoryUseCase,
                                 FindSubcategoryUseCase findSubcategoryUseCase,
                                 UpdateSubcategoryUseCase updateSubcategoryUseCase,
                                 DeleteSubcategoryUseCase deleteSubcategoryUseCase) {
        this.createSubcategoryUseCase = createSubcategoryUseCase;
        this.findSubcategoryUseCase = findSubcategoryUseCase;
        this.updateSubcategoryUseCase = updateSubcategoryUseCase;
        this.deleteSubcategoryUseCase = deleteSubcategoryUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova subcategoria")
    public ResponseEntity<ResponseSubcategoryDto> createSubcategory(@RequestBody @Valid RequestSubcategoryDto requestSubcategoryDto) {
        SubcategoryDto subcategoryDto = createSubcategoryUseCase.createSubcategory(requestSubcategoryDto.toSubcategoryDto());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseSubcategoryDto.fromSubcategoryDto(subcategoryDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma subcategoria pelo ID")
    public ResponseEntity<ResponseSubcategoryDto> getSubcategoryById(@PathVariable Long id) {
        SubcategoryDto subcategoryDto = findSubcategoryUseCase.findById(id);
        return ResponseEntity.ok(ResponseSubcategoryDto.fromSubcategoryDto(subcategoryDto));
    }

    @GetMapping
    @Operation(summary = "Lista todas as subcategorias ou filtra por categoria principal")
    public ResponseEntity<List<ResponseSubcategoryDto>> getAllSubcategories(
            @RequestParam(required = false) Category category) {
        List<SubcategoryDto> subcategoryDtos;
        if (category != null) {
            subcategoryDtos = findSubcategoryUseCase.findByCategory(category);
        } else {
            subcategoryDtos = findSubcategoryUseCase.findAll();
        }
        return ResponseEntity.ok(subcategoryDtos.stream()
                .map(ResponseSubcategoryDto::fromSubcategoryDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma subcategoria existente")
    public ResponseEntity<ResponseSubcategoryDto> updateSubcategory(@PathVariable Long id, @RequestBody @Valid RequestSubcategoryDto requestSubcategoryDto) {
        SubcategoryDto subcategoryDto = updateSubcategoryUseCase.updateSubcategory(id, requestSubcategoryDto.toSubcategoryDto());
        return ResponseEntity.ok(ResponseSubcategoryDto.fromSubcategoryDto(subcategoryDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma subcategoria")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        deleteSubcategoryUseCase.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }
}
