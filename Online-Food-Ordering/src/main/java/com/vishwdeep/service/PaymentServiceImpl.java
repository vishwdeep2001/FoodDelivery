package com.vishwdeep.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vishwdeep.model.Order;
import com.vishwdeep.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class PaymentServiceImpl implements PaymentService{
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {

        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success" + order.getId())
                .setCancelUrl("http://localhost:3000/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount((long) (order.getTotalPrice() * 100))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("zosh food")
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

        Session session = Session.create(params);
        PaymentResponse response = new PaymentResponse();
        response.setPayment_url(session.getUrl());
        return response;

    }
}
