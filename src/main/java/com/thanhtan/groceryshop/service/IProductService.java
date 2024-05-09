package com.thanhtan.groceryshop.service;

import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    List<ProductResponse> findAll();

    ProductResponse createProduct(ProductRequest product, MultipartFile file);

    List<ProductResponse> findByCategory(String categoryName);

    List<ProductResponse> find5ProductsByCategory(String categoryName);

    ProductResponse findById(Long id);

    ProductResponse findBySlug(String slug);
}
