package com.bonbravo.store.dto;

import com.bonbravo.store.entities.OrderItem;
import com.bonbravo.store.entities.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
}
