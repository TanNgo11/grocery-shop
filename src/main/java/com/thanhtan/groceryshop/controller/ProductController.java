package com.thanhtan.groceryshop.controller;

import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ApiResponse;
import com.thanhtan.groceryshop.dto.response.ProductResponse;
import com.thanhtan.groceryshop.entity.Product;
import com.thanhtan.groceryshop.repository.ProductRepository;
import com.thanhtan.groceryshop.service.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    IProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts() {
       return ApiResponse.<List<ProductResponse>>builder()
               .result(productService.findAll())
               .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(product))
                .build();
    }
}
