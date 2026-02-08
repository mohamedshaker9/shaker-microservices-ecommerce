package com.shaker.ecommicro.order.service;


import com.shaker.ecommicro.order.dto.StripePaymentDto;
import com.shaker.ecommicro.order.exceptions.ResourceNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class StripePaymentService {

//    private final AuthUtils authUtils;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    private void init() {
        Stripe.apiKey  = stripeSecretKey;
    }


    public Map<String, String> getClientSecret(StripePaymentDto stripePaymentDto) throws ResourceNotFoundException, StripeException {

        PaymentIntentCreateParams params = PaymentIntentCreateParams
                .builder()
                .setAmount(Math.round(stripePaymentDto.getAmount() * 100))
                .setCurrency("usd")
                .setCustomer(getCustomer(stripePaymentDto).getId())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

       return Map.of("client_secret", paymentIntent.getClientSecret());

    }



    private Customer getCustomer(StripePaymentDto stripePaymentDto) throws ResourceNotFoundException, StripeException {
//        User user = authUtils.getLoggedInUser();

        CustomerSearchParams params =
                CustomerSearchParams.builder()
                        .setQuery("email:'" + "user.getEmail()" + "'")
                        .build();
        CustomerSearchResult customers = Customer.search(params);

        if(customers.getData().isEmpty()){
            CustomerCreateParams createParams =
                    CustomerCreateParams.builder()
                            .setName("user.getFullName()")
                            .setEmail("user.getEmail()")
                            .build();
            return Customer.create(createParams);
        }

        return Customer.retrieve(customers.getData().get(0).getId());
    }
}
