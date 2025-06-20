package br.com.fiaplanchesproduct.domain;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testToProductDto() {
        Subcategory subcategory = new Subcategory(1L, "Subcategoria Teste", Category.LANCHE);
        Product product = new Product(1L, "Hamburguer", new BigDecimal("25.00"), Category.LANCHE, subcategory,
                "Delicioso", "key", "url", 10, true, "SKU123", "BAR123");
        ProductDto dto = product.toProductDto();
        assertEquals(product.getId(), dto.id());
        assertEquals(product.getNomeProduto(), dto.nomeProduto());
        assertEquals(product.getPreco(), dto.preco());
        assertEquals(product.getDescricao(), dto.descricao());
        assertNotNull(dto.subcategory());
        assertEquals(subcategory.getId(), dto.subcategory().id());
        assertEquals(subcategory.getName(), dto.subcategory().name());
    }

    @Test
    void testUpdateProduct() {
        Product original = new Product(1L, "Hamburguer", new BigDecimal("25.00"), Category.LANCHE, null,
                "Delicioso", "key", "url", 10, true, "SKU123", "BAR123");
        Product novo = new Product(1L, "X-Burger", new BigDecimal("30.00"), Category.LANCHE, null,
                "Mais saboroso", "key2", "url2", 20, false, "SKU456", "BAR456");
        original.updateProduct(novo);
        assertEquals("X-Burger", original.getNomeProduto());
        assertEquals(new BigDecimal("30.00"), original.getPreco());
        assertEquals("Mais saboroso", original.getDescricao());
        assertEquals(20, original.getEstoque());
        assertEquals(null, original.getSku());
        assertEquals("BAR456", original.getBarcode());
    }

    @Test
    void testAtivarDesativar() {
        Product product = new Product();
        product.desativar();
        assertFalse(product.isAtivo());
        product.ativar();
        assertTrue(product.isAtivo());
    }
}
