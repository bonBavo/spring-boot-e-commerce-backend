package com.bonbravo.store.services;

import com.bonbravo.store.dto.OrderDto;
import com.bonbravo.store.mappers.OrderMapper;
import com.bonbravo.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

     private final OrderRepository orderRepository;
     private final OrderMapper orderMapper;
     private final AuthService  authService;
    public List<OrderDto> getOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return  orders.stream().map(orderMapper::toDto).toList();

    }
}
