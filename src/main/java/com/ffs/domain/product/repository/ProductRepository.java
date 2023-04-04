package com.ffs.domain.product.repository;

import com.ffs.domain.product.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long> {

    Product save(Product product);

    @Query("SELECT p FROM Product p WHERE p.branch.id = :branchId")
    List<Product> findAllByBranchId(Long branchId);

    Optional<Product> findById(Long id);
}
