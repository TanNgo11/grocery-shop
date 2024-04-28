package com.thanhtan.groceryshop.dto.request;

import com.thanhtan.groceryshop.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * DTO for {@link OrderItem}
 */
@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest implements Serializable {
    Long id;
    int quantity;
    double price;
}