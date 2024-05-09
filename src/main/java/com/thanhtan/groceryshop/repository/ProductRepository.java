package com.thanhtan.groceryshop.repository;

import com.thanhtan.groceryshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);

    Product findByCategory_Name(String name);

    List<Product> findByCategory_Products_Price(double price);




   Optional<Product> findBySlug(String slug);
}