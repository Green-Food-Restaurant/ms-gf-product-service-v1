package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ToggleProductStatusUseCase {

    private final ProductRepositoryPortOut productRepositoryPortOut;

    public void activateProduct(Long id) {
        productRepositoryPortOut.toggleProductStatus(id, true);
    }

    public void deactivateProduct(Long id) {
        productRepositoryPortOut.toggleProductStatus(id, false);
    }
}
