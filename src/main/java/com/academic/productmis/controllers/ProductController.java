package com.academic.productmis.controllers;

import com.academic.productmis.models.Product;
import com.academic.productmis.models.dto.ProductDto;
import com.academic.productmis.services.ProductService;
import com.academic.productmis.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public List<Product> getAll() {

        return productService.getAll();
    }

    @GetMapping("/all-products/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") UUID id) {

        Product product = productService.getById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "product not found"));
    }

    @PostMapping("/save-product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto dto) {
        if (productService.existsByName(dto.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Names exists already");
        }
        Product product = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/update-product")
    public ResponseEntity<?> editProduct(@RequestParam UUID id, @RequestBody ProductDto dto) {
        Product product = productService.update(id, dto);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "product not found"));
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam UUID id) {
        Product product = productService.deleteProduct(id);
        System.out.println(product);
        return ResponseEntity.ok(product);
    }
}
