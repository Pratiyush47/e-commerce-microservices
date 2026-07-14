package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.CreatePaymentRequest;
import com.ecommerce.order.dto.OrderDTO;
import com.ecommerce.order.dto.PaymentDTO;
import com.ecommerce.order.dto.UpdateOrderRequest;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClientService productClientService;
    private final PaymentClientService paymentClientService;

    public OrderDTO createOrder(CreateOrderRequest request) {

        // Fetch product price
        BigDecimal productPrice =
                productClientService.getProductPrice(request.getProductId());

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(
                productPrice.multiply(BigDecimal.valueOf(request.getQuantity()))
        );
        order.setStatus("PENDING");
        order.setShippingAddress(request.getShippingAddress());

        Order savedOrder = orderRepository.save(order);

        CreatePaymentRequest paymentRequest =
                new CreatePaymentRequest(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getTotalPrice(),
                        "UPI"
                );

        try {

            PaymentDTO payment =
                    paymentClientService.processPayment(paymentRequest);

            if (payment != null &&
                    "SUCCESS".equalsIgnoreCase(payment.getStatus())) {

                savedOrder.setStatus("PAID");

            } else {

                savedOrder.setStatus("FAILED");
            }

        } catch (Exception e) {

            savedOrder.setStatus("FAILED");
        }

        savedOrder = orderRepository.save(savedOrder);

        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        return convertToDTO(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByStatus(String status) {

        return orderRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO updateOrder(Long id,
                                UpdateOrderRequest request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        if (request.getShippingAddress() != null) {
            order.setShippingAddress(request.getShippingAddress());
        }

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        orderRepository.delete(order);
    }

    private OrderDTO convertToDTO(Order order) {

        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}