package com.thanhtan.groceryshop.dto.request;


import com.thanhtan.groceryshop.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;



@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest implements Serializable {
    @NotBlank(message = "FILL_IN_THE_INPUT_FIELD")
    String customerName;
    @NotBlank(message = "FILL_IN_THE_INPUT_FIELD")
    String email;
    String phoneNumber;
    @NotBlank(message = "FILL_IN_THE_INPUT_FIELD")
    String address;
    String note;
    Set<OrderItem> orderItems;

}