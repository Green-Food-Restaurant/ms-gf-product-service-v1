package br.com.fiaplanchesproduct.domain;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private String nomeProduto;
    private BigDecimal preco;
    private Category category; // Categoria principal
    private Subcategory subcategory;
    private String descricao;
    private String keyImage;
    private String urlImage;
    private Integer estoque;
    private boolean ativo = true;
    private String sku;
    private String barcode;

    public Product() {
    }

    public Product(Long id, String nomeProduto, BigDecimal preco, Category category, Subcategory subcategory,
            String descricao, String keyImage, String urlImage, Integer estoque, boolean ativo, String sku,
            String barcode) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.category = category; // Categoria principal
        this.subcategory = subcategory;
        this.descricao = descricao;
        this.keyImage = keyImage;
        this.urlImage = urlImage;
        this.estoque = estoque;
        this.ativo = ativo;
        this.barcode = barcode;
    }

    public ProductDto toProductDto() {
        return new ProductDto(id,
                nomeProduto, preco, category, subcategory.toSubcategoryDto(), descricao, keyImage, urlImage,
                estoque, ativo, sku, barcode);
    }

    public Product updateProduct(Product novoProduto) {
        if (!novoProduto.getNomeProduto().isEmpty()) {
            setNomeProduto(novoProduto.getNomeProduto());
        }
        if (novoProduto.getCategory() != null) { // Categoria principal
            setCategory(novoProduto.getCategory());
        }
        if (novoProduto.getSubcategory() != null) {
            setSubcategory(novoProduto.getSubcategory());
        }
        if (novoProduto.getPreco() != null) {
            setPreco(novoProduto.getPreco());
        }
        if (novoProduto.getDescricao() != null) {
            setDescricao(novoProduto.getDescricao());
        }
        if (novoProduto.getEstoque() != null) {
            setEstoque(novoProduto.getEstoque());
        }
        if (novoProduto.getSku() != null) {
            setSku(novoProduto.getSku());
        }
        if (novoProduto.getBarcode() != null) {
            setBarcode(novoProduto.getBarcode());
        }

        return this;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}