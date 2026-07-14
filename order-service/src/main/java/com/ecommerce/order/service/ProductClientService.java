package com.ecommerce.order.service;

import com.ecommerce.order.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductClientService {

    private final RestTemplate restTemplate;

    @Value("${app.services.product-url}")
    private String productServiceUrl;

    public BigDecimal getProductPrice(Long productId) {

        String url = productServiceUrl + "/api/products/" + productId;

        ProductDTO product =
                restTemplate.getForObject(url, ProductDTO.class);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return product.getPrice();
    }
}