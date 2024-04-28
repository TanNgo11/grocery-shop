package com.thanhtan.groceryshop.dto.request;

import com.thanhtan.groceryshop.dto.response.BaseDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    String description;
    double price;
    int quantity;
    Long categoryId;

}