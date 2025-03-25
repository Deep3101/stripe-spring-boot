package com.payment.util;

import com.payment.service.PaymentProvider;
import com.payment.service.serviceimpl.RazorPayPaymentProvider;
import com.payment.service.serviceimpl.StripePaymentProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderFactory {

    private final StripePaymentProvider stripePaymentProvider;
    private final RazorPayPaymentProvider razorPayPaymentProvider;


    public PaymentProviderFactory(@Qualifier("stripePaymentProvider") StripePaymentProvider stripePaymentProvider,@Qualifier("razorPayPaymentProvider") RazorPayPaymentProvider razorPayPaymentProvider) {
        this.stripePaymentProvider = stripePaymentProvider;
        this.razorPayPaymentProvider = razorPayPaymentProvider;
    }

    public PaymentProvider getPaymentProvider(String provider) {
        switch (provider.toLowerCase()) {
            case "stripe":
                return stripePaymentProvider;
            case "razorpay":
                return razorPayPaymentProvider;
            default:
                throw new IllegalArgumentException("Unsupported payment provider: " + provider);
        }
    }

}
