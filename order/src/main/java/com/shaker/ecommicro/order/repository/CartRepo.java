package com.shaker.ecommicro.order.repository;


import com.shaker.ecommicro.order.model.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    @EntityGraph(attributePaths = "items")
    Optional<Cart> findById(Long id);

    Optional<Cart> findByUserId(Long userId);
}
