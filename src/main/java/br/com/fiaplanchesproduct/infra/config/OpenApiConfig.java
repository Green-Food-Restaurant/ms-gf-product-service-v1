package br.com.fiaplanchesproduct.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API de Gerenciamento de Produtos - FIAP Lanches")
                        .description("API para gerenciamento completo de produtos com listagem, filtros e operações CRUD")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe FIAP Lanches")
                                .url("https://github.com/fiap-lanches")
                                .email("contato@fiap-lanches.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .tags(List.of(
                        new Tag().name("Produtos").description("Operações relacionadas a produtos"),
                        new Tag().name("Imagens").description("Operações relacionadas a imagens de produtos"),
                        new Tag().name("Filtros").description("Filtros e pesquisas de produtos")
                ));
    }
}
