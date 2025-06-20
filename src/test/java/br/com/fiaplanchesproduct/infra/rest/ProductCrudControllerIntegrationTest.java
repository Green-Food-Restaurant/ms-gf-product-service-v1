package br.com.fiaplanchesproduct.infra.rest;

import br.com.fiaplanchesproduct.application.dtos.RequestProductDto;
import br.com.fiaplanchesproduct.domain.enums.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductCrudControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterAndFindProduct() throws Exception {
        // Cria um produto
        RequestProductDto request = new RequestProductDto(
                "Produto Integração", // nameProduct
                new BigDecimal("99.99"), // price
                Category.LANCHE, // category
                null, // subcategoryId (ajustado para evitar erro 400)
                "Descrição teste", // description
                "urlImage", // imageUrl
                10, // estoque
                true, // ativo
                "SKU-INT", // sku
                "BAR-INT" // barcode
        );
        // Cadastra
        String response = mockMvc.perform(post("/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeProduto").value("Produto Integração"))
                .andReturn().getResponse().getContentAsString();

        // Busca paginada
        mockMvc.perform(get("/v1/product/find"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
