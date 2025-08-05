package com.bonbravo.store.controllers;

import com.bonbravo.store.dto.CheckoutRequest;
import com.bonbravo.store.dto.CheckoutResponse;
import com.bonbravo.store.dto.ErrorDto;
import com.bonbravo.store.entities.OrderStatus;
import com.bonbravo.store.exceptions.CartEmptyException;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.exceptions.PaymentException;
import com.bonbravo.store.repositories.OrderRepository;
import com.bonbravo.store.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @PostMapping
    public CheckoutResponse checkout(
            @Valid @RequestBody CheckoutRequest checkoutRequest){
            return checkoutService.checkout(checkoutRequest);
    }

    @PostMapping("/webhook")

    public ResponseEntity<Void> handleWebhook(
            @Valid @RequestHeader ("Stripe-Signature") String signatureHeader,
            @RequestBody String payload
    ){
        try {
            var event = Webhook.constructEvent(payload, signatureHeader, webhookSecretKey);
            System.out.println(event.getType());

            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

            switch (event.getType()) {
                case "payment_intent.succeeded"->{
                    var paymentIntent = (PaymentIntent) stripeObject;
                    if (paymentIntent != null) {
                        var orderId = paymentIntent.getMetadata().get("order_id");
                        var order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                    }
                }
                case "payment_intent.failed"->{
                    // Handle payment failure
                }
            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(
                "An error occurred while processing the payment: " + e.getMessage()));
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

}
