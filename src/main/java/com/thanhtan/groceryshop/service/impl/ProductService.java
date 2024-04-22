package com.thanhtan.groceryshop.service.impl;

import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ProductResponse;
import com.thanhtan.groceryshop.entity.Product;
import com.thanhtan.groceryshop.mapper.CategoryMapper;
import com.thanhtan.groceryshop.mapper.ProductMapper;
import com.thanhtan.groceryshop.repository.CategoryRepository;
import com.thanhtan.groceryshop.repository.ProductRepository;
import com.thanhtan.groceryshop.service.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;

    CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest product) {
        Product savedProduct = productMapper.toProduct(product);
        categoryRepository.findById(product.getCategoryId()).ifPresent(savedProduct::setCategory);
        return productMapper.toProductResponse(productRepository.save(savedProduct));
    }

    @Override
    public List<ProductResponse> findAll() {
       return productRepository.findAll().stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }
}
