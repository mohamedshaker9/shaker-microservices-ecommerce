package com.shaker.ecommicro.order.repository;


import com.shaker.ecommicro.order.enums.OrderStatus;
import com.shaker.ecommicro.order.model.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecifications {

    private OrderSpecifications() {
    }

    public static Specification<Order> withOptionalStatus(OrderStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("status"), status);
        };
    }
}