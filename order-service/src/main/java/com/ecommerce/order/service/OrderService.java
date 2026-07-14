package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderDTO;
import com.ecommerce.order.dto.UpdateOrderRequest;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderDTO createOrder(CreateOrderRequest request) {

        // Fetch product price from Product Service
        BigDecimal productPrice = getProductPrice(request.getProductId());

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(productPrice.multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setStatus("PENDING");
        order.setShippingAddress(request.getShippingAddress());

        // createdAt and updatedAt are handled automatically
        // by @PrePersist in Order entity

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

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

    public OrderDTO updateOrder(Long id, UpdateOrderRequest request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        if (request.getShippingAddress() != null) {
            order.setShippingAddress(request.getShippingAddress());
        }

        // updatedAt is handled automatically
        // by @PreUpdate in Order entity

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderRepository.delete(order);
    }

    private BigDecimal getProductPrice(Long productId) {

        try {
            String url = "http://product-service:8002/api/products/" + productId;

            // Placeholder until service-to-service communication is implemented
            restTemplate.getForObject(url, Object.class);

            return BigDecimal.valueOf(100.0);

        } catch (Exception e) {

            // Temporary fallback value
            return BigDecimal.valueOf(100.0);
        }
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