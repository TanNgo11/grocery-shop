package com.thanhtan.groceryshop.repository;

import com.thanhtan.groceryshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}