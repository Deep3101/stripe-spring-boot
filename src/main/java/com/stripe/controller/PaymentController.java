package com.stripe.controller;

import com.stripe.dto.PaymentResponse;
import com.stripe.dto.ProductRequest;
import com.stripe.util.PaymentProviderFactory;
import com.stripe.service.PaymentProvider;
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
