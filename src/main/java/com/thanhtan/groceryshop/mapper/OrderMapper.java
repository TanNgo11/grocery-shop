package com.thanhtan.groceryshop.mapper;

import com.thanhtan.groceryshop.dto.request.OrderRequest;
import com.thanhtan.groceryshop.entity.Order;
import com.thanhtan.groceryshop.dto.response.OrderResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);







    OrderResponse toDto1(Order order);


}