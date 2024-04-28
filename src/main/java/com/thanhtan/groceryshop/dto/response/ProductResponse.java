package com.thanhtan.groceryshop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse extends BaseDTO{
    String name;
    String description;
    double price;
    int quantity;
    String image;
    String slug;
    CategoryResponse category;
}
