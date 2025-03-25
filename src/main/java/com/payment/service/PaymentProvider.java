package com.payment.service;

import com.payment.dto.PaymentResponse;
import com.payment.dto.ProductRequest;

public interface PaymentProvider {
    PaymentResponse createPaymentSession(ProductRequest productRequest);
}
