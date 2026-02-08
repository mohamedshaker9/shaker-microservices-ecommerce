
package com.shaker.ecommicro.order.service;


import com.shaker.ecommicro.order.dto.CartDto;
import com.shaker.ecommicro.order.dto.CartItemDetialsDto;
import com.shaker.ecommicro.order.dto.CartItemDto;
import com.shaker.ecommicro.order.dto.CartItemRequestDto;
import com.shaker.ecommicro.order.exceptions.BusinessException;
import com.shaker.ecommicro.order.exceptions.ResourceNotFoundException;
import com.shaker.ecommicro.order.model.Cart;
import com.shaker.ecommicro.order.model.CartItem;
import com.shaker.ecommicro.order.repository.CartRepo;
import com.shaker.ecommicro.order.repository.ICartItemRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepo cartRepo;
    private final ICartItemRepo cartItemRepo;
//    private final AuthUtils authUtils;

    @Transactional
    public Long save(List<CartItemRequestDto> cartItemRequests) throws ResourceNotFoundException {

        if (cartItemRequests == null || cartItemRequests.isEmpty()) {
            throw new BusinessException("Cart cannot be empty");
        }

//        User user = authUtils.getLoggedInUser();
        //TODD: get the userId
        Cart cart = cartRepo.findByUserId(1L)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(1L);
                    newCart.setItems(new ArrayList<>());
                    return newCart;
                });


        if (cart.getId() != null) {
            cart.getItems().clear();
        }

        double subtotal = 0.0;
        double totalDiscount = 0.0;


        for (CartItemRequestDto itemRequest : cartItemRequests) {
            //TODO get the product from inventory system
//            Product product = productRepo.findById(itemRequest.getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemRequest.getId()));
            //TODO validating the quantity
//            if (product.getQuantity() < itemRequest.getQuantity()) {
//                throw new BusinessException("Insufficient stock for product: " + product.getName() +
//                        ". Available: " + product.getQuantity() + ", Requested: " + itemRequest.getQuantity());
//            }

//            double itemPrice = product.getSpecialPrice() != null ? product.getSpecialPrice() : product.getPrice();
//            double itemDiscount = product.getDiscount() != null ? product.getDiscount() : 0.0;
//            double itemTotal = itemPrice * itemRequest.getQuantity();
//            double itemDiscountAmount = (product.getPrice() - itemPrice) * itemRequest.getQuantity();
            double itemPrice = 1.0;
            double itemDiscount = 0.0;
            double itemTotal = itemPrice * itemRequest.getQuantity();
            double itemDiscountAmount = (1.0 - itemPrice) * itemRequest.getQuantity();


            // TODO set the product Id
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(1L);
            cartItem.setPrice(itemPrice);
            cartItem.setQuantity(itemRequest.getQuantity());
            cartItem.setDiscount(itemDiscount);
            cart.getItems().add(cartItem);

            subtotal += itemTotal;
            totalDiscount += itemDiscountAmount;
        }

        cart.setTotal(subtotal);
        cart.setDiscount(totalDiscount);

        Cart savedCart = cartRepo.save(cart);

        return savedCart.getId();
    }

    public CartDto getCartById(Long cartId) throws ResourceNotFoundException {
        Cart cart = cartRepo.findById(cartId.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

        return mapCartToDto(cart);
    }

    public CartDto getCartForUserId(Long userId) throws ResourceNotFoundException {
//        User user = authUtils.getLoggedInUser();

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "user", userId));

        return mapCartToDto(cart);
    }

    private CartDto mapCartToDto(Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getItems().stream()
                .map(this::mapCartItemToDto)
                .collect(Collectors.toList());

        return new CartDto(
                cart.getId(),
                cartItemDtos,
                cart.getTotal(),
                cart.getDiscount()
        );
    }

    private CartItemDto mapCartItemToDto(CartItem cartItem) {
//        Product product = cartItem.getProduct();

//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setId(product.getId());
//        productDTO.setProductName(product.getName());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setSpecialPrice(product.getSpecialPrice());
//        productDTO.setDiscount(product.getDiscount());
//        productDTO.setImage(product.getImage());
//        productDTO.setQuantity(product.getQuantity());

        CartItemDetialsDto cartItemDetailsDto = new CartItemDetialsDto();
        cartItemDetailsDto.setProductId(1L);
        cartItemDetailsDto.setProductName("product.getName()");
        cartItemDetailsDto.setDescription("product.getDescription()");
        cartItemDetailsDto.setPrice(1.0);
        cartItemDetailsDto.setSpecialPrice(0.0);
        cartItemDetailsDto.setDiscount(0.0);
        cartItemDetailsDto.setImage("product.getImage()");
        cartItemDetailsDto.setQuantity(1);

        double totalPrice = cartItem.getPrice() * cartItem.getQuantity();

        return new CartItemDto(
                cartItem.getId(),
                cartItemDetailsDto,
                cartItem.getQuantity(),
                cartItem.getDiscount(),
                cartItem.getPrice(),
                totalPrice
        );
    }
}