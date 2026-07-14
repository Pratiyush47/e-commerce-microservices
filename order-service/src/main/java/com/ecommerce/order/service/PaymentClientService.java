package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreatePaymentRequest;
import com.ecommerce.order.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentClientService {

    private final RestTemplate restTemplate;

    public PaymentDTO processPayment(CreatePaymentRequest request) {

        String url = "http://localhost:8005/api/payments";

        return restTemplate.postForObject(
                url,
                request,
                PaymentDTO.class
        );
    }
}