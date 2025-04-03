package com.payment.controller;

import com.payment.dto.PaymentResponse;
import com.payment.dto.ProductRequest;
import com.payment.service.EmailService;
import com.payment.util.PaymentProviderFactory;
import com.payment.service.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProviderFactory paymentProviderFactory;
    private final EmailService emailService;

    @PostMapping("/checkout/{provider}")
    public PaymentResponse checkout(@PathVariable String provider, @RequestBody ProductRequest productRequest) {
        PaymentProvider paymentProvider = paymentProviderFactory.getPaymentProvider(provider);
        return paymentProvider.createPaymentSession(productRequest);
    }

    @PostMapping("/success")
    public String handleSuccessfulPayment(@RequestBody ProductRequest productRequest) {
        if (productRequest == null || productRequest.getEmail() == null) {
            return "Payment data is incomplete. Cannot generate invoice.";
        }
        emailService.generateInvoiceAndSendEmail(productRequest);
        return "Payment successful. Invoice sent to " + productRequest.getEmail();
    }
}
