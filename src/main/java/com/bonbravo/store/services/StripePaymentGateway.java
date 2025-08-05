package com.bonbravo.store.services;

import com.bonbravo.store.entities.Order;
import com.bonbravo.store.entities.OrderItem;
import com.bonbravo.store.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway{
    @Value("${websiteUrl}")
    // This URL should be set in your application properties or environment variables
    // It is used to redirect users after a successful or canceled payment
    // For example: website.url=https://yourdomain.com
    private String websiteUrl;
    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            // Create a Stripe Checkout session
            // This is a simplified example, you would typically include more details such as line items,
            // customer information, and other parameters as needed.
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id", order.getId().toString());

            order.getOrderItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());

            return new CheckoutSession(session.getUrl());
        }
        catch (StripeException e) {
            // Handle Stripe exceptions, log them, or rethrow as a custom exception
            System.out.println(e.getMessage());
            throw new PaymentException();
        }
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                        createPriceData(item)
                ).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice()) // Stripe expects amount in cents
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
