package com.shaker.ecommicro.order.clients;


import com.shaker.ecommicro.order.dto.ProductDTO;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface IProductServiceClient {



    @GetExchange("/api/v1/products/{id}")
    ProductDTO getProduct(@PathVariable Long id);
}
