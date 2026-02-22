package com.shaker.ecommicro.order.clients;

import com.shaker.ecommicro.order.dto.ProductDTO;
import com.shaker.ecommicro.order.exceptions.APIException;
import com.shaker.ecommicro.order.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class InventoryProductClient {

    private final IProductServiceClient client;

    public InventoryProductClient(IProductServiceClient client) {
        this.client = client;
    }

    public ProductDTO getOrThrow(Long id) throws ResourceNotFoundException {
        try {
            return client.getProduct(id);
        }
        catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        catch (HttpClientErrorException ex) {
            throw new APIException(ex.getMessage());
        }
    }
}
