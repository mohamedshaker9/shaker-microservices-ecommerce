package com.shaker.ecommicro.inventory.repository;

import com.shaker.ecommicro.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByName(String name);
    Optional<Category> findByNameIgnoreCase(String name);


}
