package com.shaker.ecommicro.order.dto;


import com.shaker.ecommicro.order.enums.OrderStatus;

public record OrdersFilter(
        OrderStatus status,
        String sortBy,
        String sortOrder,
        Integer pageSize,
        Integer pageNumber
) {
}