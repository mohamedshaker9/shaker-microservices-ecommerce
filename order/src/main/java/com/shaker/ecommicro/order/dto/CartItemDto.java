package com.shaker.ecommicro.order.dto;


public record CartItemDto (
         int id,
         CartItemDetialsDto cartItemDetailsDto,
         Integer quantity,
         double discount,
         double price,
         double totalPrice

){
}
