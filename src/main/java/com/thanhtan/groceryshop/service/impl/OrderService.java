package com.thanhtan.groceryshop.service.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thanhtan.groceryshop.dto.request.OrderItemRequest;
import com.thanhtan.groceryshop.dto.request.OrderRequest;
import com.thanhtan.groceryshop.dto.response.OrderResponse;
import com.thanhtan.groceryshop.entity.Order;
import com.thanhtan.groceryshop.entity.OrderItem;
import com.thanhtan.groceryshop.entity.Product;
import com.thanhtan.groceryshop.entity.QOrder;
import com.thanhtan.groceryshop.enums.OrderStatus;
import com.thanhtan.groceryshop.exception.AppException;
import com.thanhtan.groceryshop.exception.ErrorCode;
import com.thanhtan.groceryshop.exception.ResourceNotFound;
import com.thanhtan.groceryshop.mapper.OrderItemMapper;
import com.thanhtan.groceryshop.mapper.OrderMapper;
import com.thanhtan.groceryshop.repository.OrderRepository;
import com.thanhtan.groceryshop.repository.ProductRepository;
import com.thanhtan.groceryshop.service.IOrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {

    OrderRepository orderRepository;

    OrderMapper orderMapper;

    OrderItemMapper orderItemMapper;

    ProductRepository productRepository;


    JPAQueryFactory queryFactory;


    @Override
    public List<OrderResponse> getAllOrderWithoutOrderItems() {
        QOrder qOrder = QOrder.order;
        List<Order> orders = queryFactory
                .select(orderProjection())
                .from(qOrder)
                .orderBy(qOrder.createdDate.desc())
                .fetch();

        return orderMapper.toOrderResponseList(orders);
    }

    @Override
    public Page<OrderResponse> getAllOrderWithoutOrderItems(Pageable pageable, String searchTerm) {
        QOrder qOrder = QOrder.order;
        JPAQuery<Order> query = queryFactory
                .select(orderProjection())
                .from(qOrder)
                .where(qOrder.customerName.containsIgnoreCase(searchTerm)
                        .or(qOrder.email.containsIgnoreCase(searchTerm))
                        .or(qOrder.orderStatus.stringValue().containsIgnoreCase(searchTerm))

                ).orderBy(getOrderSpecifier(pageable.getSort(), qOrder))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        List<Order> orders = query.fetch();

        long total = queryFactory
                .selectFrom(qOrder)
                .where(qOrder.customerName.containsIgnoreCase(searchTerm)
                        .or(qOrder.email.containsIgnoreCase(searchTerm)))
                .fetchCount();

        return new PageImpl<>(orderMapper.toOrderResponseList(orders), pageable, total);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFound(ErrorCode.RESOURCE_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order savedOrder = orderMapper.toOrder(orderRequest);

        double totalPay = processOrderItems(savedOrder, orderRequest.getOrderItems());
        savedOrder.setTotalPay(totalPay);
        savedOrder.setOrderStatus(OrderStatus.PENDING);


        savedOrder = orderRepository.save(savedOrder);
        return orderMapper.toOrderResponse(savedOrder);
    }

    private double processOrderItems(Order savedOrder, List<OrderItemRequest> orderItemRequests) {
        double totalPay = 0;
        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            OrderItem orderItem = orderItemMapper.toOrderItem(orderItemRequest);
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFound(ErrorCode.RESOURCE_NOT_FOUND));

            if (orderItem.getQuantity() > product.getQuantity()) {
                throw new AppException(ErrorCode.PRODUCT_QUANTITY_EXCEEDED);
            } else {
                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            }

            orderItem.setProduct(product);
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
            totalPay += calculateItemTotal(orderItem);
        }
        savedOrder.setOrderItems(orderItems);
        return totalPay;
    }

    private double calculateItemTotal(OrderItem orderItem) {
        return orderItem.getPrice() * orderItem.getQuantity();
    }
    private QBean<Order> orderProjection() {
        QOrder qOrder = QOrder.order;
        return Projections.bean(Order.class, qOrder.id, qOrder.createdDate, qOrder.totalPay, qOrder.orderStatus);
    }


    private OrderSpecifier<?> getOrderSpecifier(Sort sort, QOrder qOrder) {
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();
            return switch (property) {
                case "createdDate" -> (direction.isAscending() ? qOrder.createdDate.asc() : qOrder.createdDate.desc());
                case "totalPay" -> (direction.isAscending() ? qOrder.totalPay.asc() : qOrder.totalPay.desc());
                default -> throw new IllegalArgumentException("Unknown property: " + property);
            };
        } else {
            return qOrder.createdDate.desc();
        }
    }
}
