package com.stripe.service.serviceimpl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.stripe.dto.PaymentResponse;
import com.stripe.dto.ProductRequest;
import com.stripe.service.PaymentProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("razorPayPaymentProvider")
public class RazorPayPaymentProvider implements PaymentProvider {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String secretKey;

    @Override
    public PaymentResponse createPaymentSession(ProductRequest productRequest) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, secretKey);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", productRequest.getAmount() * 100); // Razorpay takes amount in paise
            orderRequest.put("currency", productRequest.getCurrency() != null ? productRequest.getCurrency() : "INR");
            orderRequest.put("receipt", "txn_123456");

            Order order = razorpayClient.orders.create(orderRequest);

            return PaymentResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(order.get("id")) // Order ID
                    .key(apiKey)
                    .amount(orderRequest.getInt("amount"))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
