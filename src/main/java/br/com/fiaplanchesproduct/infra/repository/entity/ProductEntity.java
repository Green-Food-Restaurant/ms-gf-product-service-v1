package br.com.fiaplanchesproduct.infra.repository.entity;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.dtos.SubcategoryDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;
    private BigDecimal preco;
    @Enumerated(EnumType.STRING)
    private Category category; // Mantido para referência direta à categoria principal

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private SubcategoryEntity subcategory;

    private String descricao;
    private String keyImage;
    @Column(length = 1000)
    private String urlImage;
    private Integer estoque;
    private boolean ativo = true;
    private String sku;
    private String barcode;

    public static ProductEntity toProductEntity(ProductDto productDTO) {
        ProductEntity entity = new ProductEntity();
        entity.setId(productDTO.id());
        entity.setNomeProduto(productDTO.nomeProduto());
        entity.setPreco(productDTO.preco());
        entity.setCategory(productDTO.category()); // Categoria principal
        if (productDTO.subcategory() != null) {
            entity.setSubcategory(productDTO.subcategory().toSubcategoryEntity());
        }
        entity.setDescricao(productDTO.descricao());
        entity.setKeyImage(productDTO.keyImage());
        entity.setUrlImage(productDTO.urlImage());
        entity.setEstoque(productDTO.estoque());
        entity.setAtivo(productDTO.ativo());
        entity.setSku(productDTO.sku());
        entity.setBarcode(productDTO.barcode());
        return entity;
    }

    public ProductDto toProductDto() {
        return new ProductDto(
                this.id,
                this.nomeProduto,
                this.preco,
                this.category, // Categoria principal
                this.subcategory != null ? SubcategoryDto.fromSubcategoryEntity(this.subcategory) : null,
                this.descricao,
                this.keyImage,
                this.urlImage,
                this.estoque,
                this.ativo,
                this.sku,
                this.barcode
        );
    }
}
