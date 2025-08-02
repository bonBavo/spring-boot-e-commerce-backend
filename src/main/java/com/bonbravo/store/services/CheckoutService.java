package com.bonbravo.store.services;

import com.bonbravo.store.dto.CheckoutRequest;
import com.bonbravo.store.dto.CheckoutResponse;
import com.bonbravo.store.dto.ErrorDto;
import com.bonbravo.store.entities.Order;
import com.bonbravo.store.exceptions.CartEmptyException;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.repositories.CartRepository;
import com.bonbravo.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;

    public CheckoutResponse checkout(CheckoutRequest checkoutRequest){
        var cart = cartRepository.getCartWithItems(checkoutRequest.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()){
           throw new CartEmptyException();
        }

        var order = Order.createOrderFromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
