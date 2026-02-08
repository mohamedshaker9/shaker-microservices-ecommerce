package com.shaker.ecommicro.order.repository;


import com.shaker.ecommicro.order.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShippingAddressRepo extends JpaRepository<ShippingAddress,Long> {

    List<ShippingAddress> findByUserId(Long userId);

    Optional<ShippingAddress> findByIdAndUserId(Long id, Long userId);
}
