package com.thanhtan.groceryshop.service;

import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ProductResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> findAll();

    ProductResponse createProduct(ProductRequest product);
}
