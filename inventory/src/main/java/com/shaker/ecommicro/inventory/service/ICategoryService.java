package com.shaker.ecommicro.inventory.service;


import com.shaker.ecommicro.inventory.dto.CategoryDTO;
import com.shaker.ecommicro.inventory.dto.CategoryResponse;
import com.shaker.ecommicro.inventory.exceptions.ResourceNotFoundException;
import com.shaker.ecommicro.inventory.model.Category;

public interface ICategoryService {

   CategoryResponse getCategories(int pageNumber, int pageSize) throws ResourceNotFoundException;
   Long create(CategoryDTO categoryDto);
   Category getById(Long id) throws ResourceNotFoundException;
   CategoryDTO update(Long id,  CategoryDTO categoryDto) throws ResourceNotFoundException;
   void delete(Long id) throws ResourceNotFoundException;
}
