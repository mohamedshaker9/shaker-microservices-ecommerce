package com.shaker.ecommicro.order.controller;

import com.shaker.ecommicro.order.dto.CartDto;
import com.shaker.ecommicro.order.dto.CartItemRequestDto;
import com.shaker.ecommicro.order.exceptions.ResourceNotFoundException;
import com.shaker.ecommicro.order.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveCart(
            @RequestBody List<CartItemRequestDto> cartItems) throws ResourceNotFoundException {
        Long cartId = cartService.save(cartItems);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Cart saved successfully",
                        "cartId", cartId
                ));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long cartId) throws ResourceNotFoundException {
        CartDto cartDto = cartService.getCartById(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDto> getCartForCurrentUser(@PathVariable Long userId) throws ResourceNotFoundException {
        CartDto cartDto = cartService.getCartForUserId(userId);
        return ResponseEntity.ok(cartDto);
    }


}
