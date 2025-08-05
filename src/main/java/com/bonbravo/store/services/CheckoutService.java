package com.bonbravo.store.services;

import com.bonbravo.store.dto.CheckoutRequest;
import com.bonbravo.store.dto.CheckoutResponse;
import com.bonbravo.store.entities.Order;
import com.bonbravo.store.exceptions.CartEmptyException;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.exceptions.PaymentException;
import com.bonbravo.store.repositories.CartRepository;
import com.bonbravo.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;



    @Transactional
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        var cart = cartRepository.getCartWithItems(checkoutRequest.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()){
           throw new CartEmptyException();
        }

        var order = Order.createOrderFromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
//        cartService.clearCart(cart.getId());

        try {
         var session = paymentGateway.createCheckoutSession(order);
//            session.getUrl(); // This URL is where the user will be redirected to complete the payment
            // Optionally, you can store the session ID in the order for future reference
            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException e) {
            // Handle Stripe exceptions, log them, or rethrow as a custom exception
            orderRepository.delete(order); // Rollback order creation on error
            throw e;
        }


    }
}
