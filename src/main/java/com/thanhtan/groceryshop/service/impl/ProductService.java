package com.thanhtan.groceryshop.service.impl;

import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ProductResponse;
import com.thanhtan.groceryshop.entity.Product;
import com.thanhtan.groceryshop.exception.ErrorCode;
import com.thanhtan.groceryshop.exception.ResourceNotFound;
import com.thanhtan.groceryshop.mapper.ProductMapper;
import com.thanhtan.groceryshop.repository.CategoryRepository;
import com.thanhtan.groceryshop.repository.ProductRepository;
import com.thanhtan.groceryshop.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;

    CategoryRepository categoryRepository;

    CloudinaryService cloudinaryService;

    @Override
    public List<ProductResponse> findByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest product, MultipartFile file) {
        Product newProduct = productMapper.toProduct(product);

        Product savedProduct = categoryRepository.findById(product.getCategoryId())
                .map(category -> {
                    newProduct.setCategory(category);
                    return productRepository.save(newProduct);
                })
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.RESOURCE_NOT_FOUND));

        Map data = cloudinaryService.upload(file);
        if (data != null) {
            savedProduct.setImage((String) data.get("url"));
        }

        savedProduct = productRepository.save(savedProduct);

        String slug = createSlug(savedProduct);

        savedProduct.setSlug(slug);
        savedProduct = productRepository.save(savedProduct);

        return productMapper.toProductResponse(savedProduct);
    }


    @Override
    public ProductResponse findBySlug(String slug) {
        return productRepository.findBySlug(slug)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    private String createSlug(Product product) {
        return product.getName().toLowerCase().replace(" ", "-") + "-" + product.getId();
    }
}
