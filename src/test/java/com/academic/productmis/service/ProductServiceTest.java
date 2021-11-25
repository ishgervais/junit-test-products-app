package com.academic.productmis.service;

import com.academic.productmis.models.Product;
import com.academic.productmis.models.dto.ProductDto;
import com.academic.productmis.repositories.IProductRepository;
import com.academic.productmis.services.ProductService;
import com.academic.productmis.utils.Exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private IProductRepository productRepositoryMock;

    @InjectMocks
    private ProductService productService;

    @Test
    public void getAllProducts() {
        when(productRepositoryMock.findAll()).thenReturn(Arrays.asList(new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10),
                new Product(UUID.fromString("edcd99ae-157a-4c88-8a44-405d93b4f18a"),"Orange", 300, 20)));
        assertEquals(100, productService.getAll().get(0).getPrice());
    }

    @Test
    public void getOneProductWhenIdIsFound() {
        when(productRepositoryMock.findById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"))).thenReturn(Optional.of(new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10)));
        assertEquals(100, productService.getById(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc")).getPrice());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOneProduct_notfound(){
        doThrow(new ResourceNotFoundException("product", "id", UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff47b2c"))).when(productRepositoryMock).getById(UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff47b2d"));
        productService.getById(UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff48b2c"));
    }


    @Test
    public void saveOneProduct() {
        ProductDto dto = new ProductDto("Orange",300, 30);
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10);
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);
        assertEquals(100, productService.create(dto).getPrice());
    }

    @Test
    public void update_Success() {
        ProductDto dto = new ProductDto("Orange",300, 20);
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10);
        when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepositoryMock.save(product)).thenReturn(product);
        Product updateProduct = productService.update(product.getId(), dto);
        assertEquals(300, updateProduct.getPrice());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateEmployee_notfound(){
        doThrow(new ResourceNotFoundException("product", "id", UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff47b2c"))).when(productRepositoryMock).findById(UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff47b2d"));
        ProductDto dto = new ProductDto("Orange",300, 30);
        productService.update(UUID.fromString("bc6bd171-790d-4f07-8943-a9f57ff47b2c"),dto);
    }

    @Test
    public void deleteProduct_Success(){
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10);
        when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
        productService.deleteProduct(product.getId());
        verify(productRepositoryMock).deleteById(product.getId());
    }
    @Test
    public void deleteProduct_404(){
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-632914827bdc"), "Orange",300, 30);
        when(productRepositoryMock.findById(product.getId())).thenReturn(Optional.of(product));
        productService.deleteProduct(product.getId());
        verify(productRepositoryMock).deleteById(product.getId());
    }
}

