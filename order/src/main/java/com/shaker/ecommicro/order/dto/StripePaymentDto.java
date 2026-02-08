package com.shaker.ecommicro.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripePaymentDto {

    private Double amount;
    private String currency;
    private Long selectedShippingAddressId;
}
