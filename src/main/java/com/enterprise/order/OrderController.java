package com.enterprise.order.controller;

import com.enterprise.order.dto.OrderRequest;
import com.enterprise.order.dto.OrderResponse;
import com.enterprise.order.service.OrderProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    public OrderController(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> submitOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderProcessingService.createOrder(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
