package br.com.fiaplanchesproduct.infra.adapters.out;

import br.com.fiaplanchesproduct.application.dtos.ProductDto;
import br.com.fiaplanchesproduct.application.ports.out.ProductRepositoryPortOut;
import br.com.fiaplanchesproduct.domain.enums.Category;
import br.com.fiaplanchesproduct.infra.repository.PostGresProductRepository;
import br.com.fiaplanchesproduct.infra.repository.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapterOut implements ProductRepositoryPortOut {

    private final PostGresProductRepository postGresProductRepository;

    public ProductRepositoryAdapterOut(PostGresProductRepository postGresProductRepository) {
        this.postGresProductRepository = postGresProductRepository;
    }

    @Override
    public Page<ProductDto> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = postGresProductRepository.findAll(pageable);
        return productEntities.map(ProductEntity::toProductDto);
    }

    @Override
    public Optional<List<ProductDto>> findProductByCategory(Category category) {
        var productEntityList = postGresProductRepository.findByCategory(category);
        return Optional.of(productEntityList.stream().map(ProductEntity::toProductDto).toList());
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        return postGresProductRepository.save(ProductEntity.toProductEntity(productDto)).toProductDto();
    }

    @Override
    public void deleteProduct(ProductDto productDto) {
        postGresProductRepository.delete(ProductEntity.toProductEntity(productDto));
    }

    @Override
    public Optional<ProductDto> findProductById(Long id) {
        var productEntity = postGresProductRepository.findById(id);
        return productEntity.map(ProductEntity::toProductDto);
    }

    @Override
    public Optional<List<ProductDto>> findProductsByIds(List<Long> ids) {
        var productEntityList = postGresProductRepository.findAllById(ids);
        return Optional.of(productEntityList.stream().map(ProductEntity::toProductDto).toList());
    }
}
