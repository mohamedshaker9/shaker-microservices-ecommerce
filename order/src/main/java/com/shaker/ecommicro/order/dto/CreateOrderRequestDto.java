package com.shaker.ecommicro.order.dto;


import com.shaker.ecommicro.order.enums.PaymentType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDto {
    private Long shippingAddressId;
    private String paymentIntentId;
    private PaymentType paymentType;
    private Long userId;
    private ShippingAddressDto shippingAddressDto;


}