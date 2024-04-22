package com.thanhtan.groceryshop.service;

import com.thanhtan.groceryshop.dto.request.CategoryRequest;
import com.thanhtan.groceryshop.dto.response.CategoryResponse;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest category);
}
