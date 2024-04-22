package com.thanhtan.groceryshop.mapper;

import com.thanhtan.groceryshop.dto.request.CategoryRequest;
import com.thanhtan.groceryshop.dto.response.CategoryResponse;
import com.thanhtan.groceryshop.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "status", target = "status")
    Category toCategory(CategoryRequest request);

    @Mapping(source = "status", target = "status")
    CategoryResponse toCategoryResponse(Category category);
}
