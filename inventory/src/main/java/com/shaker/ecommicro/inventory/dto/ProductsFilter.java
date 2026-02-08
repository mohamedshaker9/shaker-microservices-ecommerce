package com.shaker.ecommicro.inventory.dto;

public record ProductsFilter(
        String q,
        String sortBy,
        String sortOrder,
        String category,
        Integer pageSize,
        Integer pageNumber
) {
}
