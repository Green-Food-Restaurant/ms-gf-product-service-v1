package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ToggleProductStatusUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public ProductDto activateProduct(Long id) {
        ProductDto productDto = productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o ID: " + id));
        
        if (productDto.ativo()) {
            throw new ProductBusinessException("Produto já está ativado");
        }
        
        productRepositoryPortOut.toggleProductStatus(id, true);
        
        return productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Erro ao recuperar produto após ativação"));
    }

    public ProductDto deactivateProduct(Long id) {
        ProductDto productDto = productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o ID: " + id));
        
        if (!productDto.ativo()) {
            throw new ProductBusinessException("Produto já está desativado");
        }
        
        productRepositoryPortOut.toggleProductStatus(id, false);
        
        return productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Erro ao recuperar produto após desativação"));
    }
    
    public ProductDto toggleProductStatus(Long id, Boolean active) {
        ProductDto productDto = productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o ID: " + id));
        
        if (productDto.ativo() == active) {
            throw new ProductBusinessException("Produto já está " + (active ? "ativado" : "desativado"));
        }
        
        productRepositoryPortOut.toggleProductStatus(id, active);
        
        return productRepositoryPortOut.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Erro ao recuperar produto após alteração de status"));
    }
}
