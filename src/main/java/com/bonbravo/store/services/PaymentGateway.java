package com.bonbravo.store.services;

import com.bonbravo.store.entities.Order;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order orderId);
//
//    /**
//     * Processes a payment for the given order.
//     *
//     * @param orderId The ID of the order to process payment for.
//     * @return A response indicating the result of the payment processing.
//     * @throws PaymentProcessingException If an error occurs during payment processing.
//     */
//    PaymentResponse processPayment(String orderId) throws PaymentProcessingException;
//
//    /**
//     * Refunds a payment for the given order.
//     *
//     * @param orderId The ID of the order to refund.
//     * @return A response indicating the result of the refund processing.
//     * @throws PaymentProcessingException If an error occurs during refund processing.
//     */
//    PaymentResponse refundPayment(String orderId) throws PaymentProcessingException;
}
