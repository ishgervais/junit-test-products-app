package com.academic.productmis.repositories;

import com.academic.productmis.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IApartmentRepository extends JpaRepository<Apartment, UUID> {
    boolean existsByName(String name);
}
