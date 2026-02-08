package com.shaker.ecommicro.order.dto;

import java.util.List;

public record PaginatedResponse<T>( List<T> content,
     int pageNumber,
     int pageSize,
     int totalPages,
     long total,
     boolean isLastPage){
}
