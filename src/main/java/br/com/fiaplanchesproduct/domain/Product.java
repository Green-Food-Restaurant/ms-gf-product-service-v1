package br.com.fiaplanchesproduct.domain;

import br.com.fiaplanchesproduct.domain.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private String nomeProduto;
    private BigDecimal preco;
    private Category category;
    private String descricao;
    private String keyImage;
    private String urlImage;

    public Product() {
    }

    public Product(Long id, String nomeProduto, BigDecimal preco, Category category, String descricao, String keyImage, String urlImage) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.category = category;
        this.descricao = descricao;
        this.keyImage = keyImage;
        this.urlImage = urlImage;
    }

    public Product updateProduct(Product novoProduto) {
        if (!novoProduto.getNomeProduto().isEmpty()) {
            setNomeProduto(novoProduto.getNomeProduto());
        }
        if (novoProduto.getCategory() != null) {
            setCategory(novoProduto.getCategory());
        }
        if (novoProduto.getPreco() != null) {
            setPreco(novoProduto.getPreco());
        }

        return this;
    }
}