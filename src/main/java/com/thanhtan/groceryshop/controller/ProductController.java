package com.thanhtan.groceryshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtan.groceryshop.dto.request.ProductRequest;
import com.thanhtan.groceryshop.dto.response.ApiResponse;
import com.thanhtan.groceryshop.dto.response.ProductResponse;
import com.thanhtan.groceryshop.service.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import static com.thanhtan.groceryshop.constant.PathConstant.*;


@RestController
@RequestMapping(API_V1_PRODUCTS)
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> createProduct(@RequestPart("product") String productStr,
                                                      @RequestPart("file") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest = objectMapper.readValue(productStr, ProductRequest.class);

        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productRequest, file))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findById(id))
                .build();
    }

    @GetMapping("/detail/{slug}")
    public ApiResponse<ProductResponse> getProductBySlug(@PathVariable String slug) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findBySlug(slug))
                .build();
    }

    @GetMapping("/category/{categoryName}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryName) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.findByCategory(categoryName))
                .build();
    }

        @GetMapping("/list")
        public ApiResponse<List<ProductResponse>> getProductsByIds(@RequestParam List<Long> ids) {
            List<ProductResponse> products = ids.stream()
                    .map(productService::findById)
                    .toList();
            return ApiResponse.<List<ProductResponse>>builder()
                    .result(products)
                    .build();
        }
}
