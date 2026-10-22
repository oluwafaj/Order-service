package com.enterprise.order.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(
        String orderId,
        String customerId,
        String status,
        BigDecimal totalAmount,
        Instant timestamp
) {}
