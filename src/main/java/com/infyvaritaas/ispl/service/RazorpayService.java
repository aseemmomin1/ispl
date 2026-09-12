package com.infyvaritaas.ispl.service;

import com.infyvaritaas.ispl.config.RazorpayProperties;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    private final RazorpayProperties razorpayProperties;
    private RazorpayClient client;

    public RazorpayService(RazorpayProperties razorpayProperties) {
        this.razorpayProperties = razorpayProperties;
        if (isConfigured()) {
            try {
                this.client = new RazorpayClient(razorpayProperties.getKeyId(), razorpayProperties.getKeySecret());
            } catch (Exception ex) {
                this.client = null;
            }
        } else {
            this.client = null;
        }
    }

    public PaymentOrderResult createOrder(BigDecimal subtotal, String receipt) {
        BigDecimal gst = subtotal.multiply(razorpayProperties.getGstRate()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(gst).setScale(2, RoundingMode.HALF_UP);
        int amountInPaise = total.multiply(BigDecimal.valueOf(100)).intValue();

        if (client == null) {
            return new PaymentOrderResult("mock_order_" + UUID.randomUUID(), amountInPaise, total, gst);
        }

        try {
            JSONObject request = new JSONObject()
                    .put("amount", amountInPaise)
                    .put("currency", razorpayProperties.getCurrency())
                    .put("receipt", receipt)
                    .put("payment_capture", 1);
            Order order = client.orders.create(request);
            return new PaymentOrderResult(order.get("id"), amountInPaise, total, gst);
        } catch (Exception ex) {
            return new PaymentOrderResult("mock_order_" + UUID.randomUUID(), amountInPaise, total, gst);
        }
    }

    public boolean isConfigured() {
        return razorpayProperties != null
                && razorpayProperties.getKeyId() != null
                && !"dummy".equalsIgnoreCase(razorpayProperties.getKeyId())
                && razorpayProperties.getKeySecret() != null
                && !"dummy".equalsIgnoreCase(razorpayProperties.getKeySecret());
    }

    public Map<String, Object> buildConfirmationPayload(String orderId, String paymentId, String signature, String status) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("razorpayOrderId", orderId);
        payload.put("razorpayPaymentId", paymentId);
        payload.put("razorpaySignature", signature);
        payload.put("status", status);
        return payload;
    }

    public record PaymentOrderResult(String orderId, int amountInPaise, BigDecimal totalAmount, BigDecimal gstAmount) {
    }
}
