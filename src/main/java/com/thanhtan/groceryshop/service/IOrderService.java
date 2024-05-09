package com.thanhtan.groceryshop.service;

import com.thanhtan.groceryshop.dto.request.OrderRequest;
import com.thanhtan.groceryshop.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.cert.CertPathBuilder;
import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getAllOrderWithoutOrderItems();

    Page<OrderResponse> getAllOrderWithoutOrderItems(Pageable pageable,String searchTerm);
}
