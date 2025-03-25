package com.payment.controller;

import com.payment.dto.PaymentResponse;
import com.payment.dto.ProductRequest;
import com.payment.util.PaymentProviderFactory;
import com.payment.service.PaymentProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentProviderFactory paymentProviderFactory;

    public PaymentController(PaymentProviderFactory paymentProviderFactory) {
        this.paymentProviderFactory = paymentProviderFactory;
    }

    @PostMapping("/checkout/{provider}")
    public PaymentResponse checkout(@PathVariable String provider, @RequestBody ProductRequest productRequest) {
        PaymentProvider paymentProvider = paymentProviderFactory.getPaymentProvider(provider);
        return paymentProvider.createPaymentSession(productRequest);
    }

}
