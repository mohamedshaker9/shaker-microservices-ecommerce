package com.shaker.ecommicro.inventory.service;


import com.shaker.ecommicro.inventory.dto.ProductDTO;
import com.shaker.ecommicro.inventory.dto.ProductResponse;
import com.shaker.ecommicro.inventory.dto.ProductsFilter;
import com.shaker.ecommicro.inventory.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {

    ProductDTO add(ProductDTO productDTO, Long categoryId) throws ResourceNotFoundException;

    ProductResponse get(ProductsFilter productsFilter) throws ResourceNotFoundException;
    ProductDTO get(Long productId) throws ResourceNotFoundException;
    ProductResponse getByCategory(Long categoryId) throws ResourceNotFoundException;
     ProductResponse getByCategory(String name) throws ResourceNotFoundException;
    ProductResponse getByKeyword(String keyword) throws ResourceNotFoundException;

    ProductDTO update(ProductDTO productDTO, Long productId) throws ResourceNotFoundException;

    void delete(Long productId) throws ResourceNotFoundException;

    String uploadImage(Long productId, MultipartFile file) throws IOException, ResourceNotFoundException;
}
