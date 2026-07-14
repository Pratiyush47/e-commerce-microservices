package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreatePaymentRequest;
import com.ecommerce.order.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final RestTemplate restTemplate;

    @Value("${app.services.payment-url}")
    private String paymentServiceUrl;

    public PaymentDTO processPayment(CreatePaymentRequest request) {

        String url = paymentServiceUrl + "/api/payments";

        return restTemplate.postForObject(
                url,
                request,
                PaymentDTO.class
        );
    }
}