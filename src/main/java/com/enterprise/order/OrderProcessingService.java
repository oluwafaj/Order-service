package com.enterprise.order.service;

import com.enterprise.order.dto.OrderRequest;
import com.enterprise.order.dto.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderProcessingService {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessingService.class);

    public OrderResponse createOrder(OrderRequest request) {
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        BigDecimal total = request.amount().multiply(BigDecimal.valueOf(request.quantity()));

        log.info("Received order {} for customer {}", orderId, request.customerId());

        // Trigger background processing asynchronously
        processOrderAsync(orderId);

        return new OrderResponse(
                orderId,
                request.customerId(),
                "ACCEPTED",
                total,
                Instant.now()
        );
    }

    @Async("orderExecutor")
    public void processOrderAsync(String orderId) {
        try {
            log.info("Starting background fulfillment for order {}", orderId);
            Thread.sleep(3000); // Simulate external payment/inventory work
            log.info("Order {} successfully processed and fulfilled", orderId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Order processing interrupted for order {}", orderId, e);
        }
    }
}
