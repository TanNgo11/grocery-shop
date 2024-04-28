package com.thanhtan.groceryshop.mapper;

import com.thanhtan.groceryshop.dto.response.OrderItemResponse;
import com.thanhtan.groceryshop.entity.OrderItem;
import com.thanhtan.groceryshop.dto.request.OrderItemRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
    OrderItem toOrderItem(OrderItemRequest orderItemRequest);

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);


}