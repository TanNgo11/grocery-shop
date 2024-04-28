package com.thanhtan.groceryshop.dto.response;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.thanhtan.groceryshop.entity.OrderItem}
 */
@Value
@SuperBuilder
public class OrderItemResponse extends BaseDTO implements Serializable {

    int quantity;
    double price;
    double total;
}