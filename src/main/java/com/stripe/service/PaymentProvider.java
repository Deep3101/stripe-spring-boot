package com.stripe.service;

import com.stripe.dto.PaymentResponse;
import com.stripe.dto.ProductRequest;

public interface PaymentProvider {
    PaymentResponse createPaymentSession(ProductRequest productRequest);
}
