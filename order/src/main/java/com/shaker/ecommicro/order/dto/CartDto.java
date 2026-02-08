package com.shaker.ecommicro.order.dto;

import java.util.List;

public record CartDto(
        Long id,
        List<CartItemDto> cartDtoList,
        double subtotal,
        double discount
) {
}
