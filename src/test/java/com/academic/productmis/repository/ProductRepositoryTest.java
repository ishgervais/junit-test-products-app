package com.academic.productmis.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.academic.productmis.models.Product;
import com.academic.productmis.repositories.IProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private IProductRepository productRepository;

    @Test
    public void findAll_success () {
        List<Product> product = productRepository.findAll();
        assertEquals(4, product.size());
    }
    @Test
    public void findOne_success() {
        Optional<Product> productOption = productRepository.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));
        assertTrue(productOption.isPresent());
    }
    @Test
    public void findOne_fail() {
        Optional<Product> productOption = productRepository.findById(UUID.fromString("8f354825-e13f-4f3f-b0ad-e3d2fceccfbc"));
        assertFalse(productOption.isPresent());
    }
    @Test
    public void saveOne() {
        Product product = new Product("Mango", 100,20);
        Product productOption = productRepository.save(product);
        assertNotNull(productOption.getId());
    }
    @Test
    public void deleteWithSuccess() {
        productRepository.deleteById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));
        List<Product> product = productRepository.findAll();
        assertEquals(3, product.size());
    }
    @Test
    public void delete_Fail(){
        Optional<Product> product = productRepository.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914837bdc"));
        if (product.isPresent()) productRepository.deleteById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"));

        assertFalse(product.isPresent());
    }
}
