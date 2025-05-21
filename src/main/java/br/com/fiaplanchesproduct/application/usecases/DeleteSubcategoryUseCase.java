package br.com.fiaplanchesproduct.application.usecases;

import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.application.ports.out.SubcategoryRepositoryPortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductBusinessException;
import br.com.fiaplanchesproduct.infra.exception.handler.ProductNotFoundException;

public class DeleteSubcategoryUseCase {

    private final SubcategoryRepositoryPortOut subcategoryRepositoryPortOut;
    private final ProductRepositoryPortOut productRepositoryPortOut;

    public DeleteSubcategoryUseCase(SubcategoryRepositoryPortOut subcategoryRepositoryPortOut, ProductRepositoryPortOut productRepositoryPortOut) {
        this.subcategoryRepositoryPortOut = subcategoryRepositoryPortOut;
        this.productRepositoryPortOut = productRepositoryPortOut;
    }

    public void deleteSubcategory(Long id) {
        // Verifica se a subcategoria existe
        subcategoryRepositoryPortOut.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Subcategoria não encontrada com o ID: " + id));

        // Verifica se a subcategoria está sendo usada por algum produto
        if (productRepositoryPortOut.existsBySubcategoryId(id)) {
            throw new ProductBusinessException("Subcategoria não pode ser excluída pois está em uso por produtos.");
        }

        subcategoryRepositoryPortOut.deleteById(id);
    }
}
