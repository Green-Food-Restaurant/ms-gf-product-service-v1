package br.com.fiaplanchesproduct.infra.repository.entity;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
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
    private Category category;
    private String descricao;
    private String keyImage;
    @Column(length = 1000)
    private String urlImage;
    private Integer estoque;
    private boolean ativo = true;    public static ProductEntity toProductEntity(ProductDto productDTO) {
        return new ProductEntity(
                productDTO.id(),
                productDTO.nomeProduto(),
                productDTO.preco(),
                productDTO.category(),
                productDTO.descricao(),
                productDTO.keyImage(),
                productDTO.urlImage(),
                productDTO.estoque(),
                productDTO.ativo()
        );
    }    public ProductDto toProductDto() {
        return new ProductDto(
                this.id,
                this.nomeProduto,
                this.preco,
                this.category,
                this.descricao,
                this.keyImage,
                this.urlImage,
                this.estoque,
                this.ativo
        );
    }
}
