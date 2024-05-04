package com.vishwdeep.service;

import com.stripe.exception.StripeException;
import com.vishwdeep.model.Order;
import com.vishwdeep.response.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
