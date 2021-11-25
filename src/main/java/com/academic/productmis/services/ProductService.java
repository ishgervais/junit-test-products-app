package com.academic.productmis.services;

import com.academic.productmis.models.Product;
import com.academic.productmis.models.dto.ProductDto;
import com.academic.productmis.repositories.IProductRepository;
import com.academic.productmis.utils.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private IProductRepository productRepository;

    public List<Product> getAll() {
        System.out.println(productRepository.findAll());
        return productRepository.findAll();
    }

    public Product getById(UUID id) throws ResourceNotFoundException {

            Optional<Product> findById = productRepository.findById(id);
            if (findById.isPresent()) {
                return findById.get();
            }
            throw new ResourceNotFoundException("Product", "id", id);
    }

    public Product create(ProductDto prod) {
        System.out.println(prod);
        Product product = new Product();
        product.setName(prod.getName());
        product.setPrice(prod.getPrice());
        product.setQuantity(prod.getQuantity());

        return productRepository.save(product);
    }

    public Product update(UUID id, ProductDto prodDto) throws ResourceNotFoundException {
        Optional<Product> productExists = this.productRepository.findById(id);

            if (productExists.isPresent()) {
                Product product = productExists.get();
                product.setName(prodDto.getName());
                product.setPrice(prodDto.getPrice());
                product.setQuantity(prodDto.getQuantity());
                return productRepository.save(product);
            }
            throw new ResourceNotFoundException("product", "id", id);

    }

    public Product deleteProduct(UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id" + id));
        productRepository.deleteById(id);
        return null;
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}

