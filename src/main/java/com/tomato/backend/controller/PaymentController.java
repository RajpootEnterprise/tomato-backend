package com.tomato.backend.controller;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @PostMapping("/create-intent")
    public ResponseEntity<ApiResponse<Map<String, String>>> createPaymentIntent(@RequestBody Map<String, Object> request) {
        try {
            double amount = Double.parseDouble(request.get("amount").toString());
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // convert to cents
                    .setCurrency("usd")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("clientSecret", paymentIntent.getClientSecret());
            responseData.put("id", paymentIntent.getId());

            return ResponseEntity.ok(ApiResponse.success("Payment intent created", responseData));
        } catch (Exception e) {
            throw new BadRequestException("Failed to create Stripe PaymentIntent: " + e.getMessage());
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Map<String, Object>>> confirmPayment(@RequestBody Map<String, String> request) {
        try {
            String paymentIntentId = request.get("paymentIntentId");
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", intent.getStatus());
            responseData.put("success", "succeeded".equals(intent.getStatus()));

            return ResponseEntity.ok(ApiResponse.success("Payment status verified", responseData));
        } catch (Exception e) {
            throw new BadRequestException("Failed to verify Stripe PaymentIntent: " + e.getMessage());
        }
    }
}
