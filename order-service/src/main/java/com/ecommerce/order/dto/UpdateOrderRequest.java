package com.ecommerce.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;
}