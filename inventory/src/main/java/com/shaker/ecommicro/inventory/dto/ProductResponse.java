package com.shaker.ecommicro.inventory.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductResponse {

    private List<ProductDTO> products;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long total;
    private boolean isLastPage;

}
