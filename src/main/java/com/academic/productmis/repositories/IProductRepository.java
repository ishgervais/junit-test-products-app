package com.academic.productmis.repositories;

import com.academic.productmis.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);
}
