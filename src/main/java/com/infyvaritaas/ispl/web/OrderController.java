package com.infyvaritaas.ispl.web;

import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.Order;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.dto.ApiResponse;
import com.infyvaritaas.ispl.dto.PaymentCallbackRequest;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import com.infyvaritaas.ispl.repository.UserRepository;
import com.infyvaritaas.ispl.service.OrderService;
import com.infyvaritaas.ispl.service.RazorpayService;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final RazorpayService razorpayService;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository serviceRepository;

    public OrderController(OrderService orderService,
            RazorpayService razorpayService,
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            RepairServiceRepository serviceRepository) {
        this.orderService = orderService;
        this.razorpayService = razorpayService;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping("/orders/create")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createOrder(@RequestParam Long deviceId,
            @RequestParam Long serviceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();
        var user = userRepository.findByUsername(principalName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow();
        RepairService service = serviceRepository.findById(serviceId).orElseThrow();
        Order order = orderService.createOrder(user.getId(), device.getId(), service.getId());

        RazorpayService.PaymentOrderResult paymentOrder = razorpayService.createOrder(order.getTotalAmount(), "order-" + order.getId());
        order.setRazorpayOrderId(paymentOrder.orderId());
        var savedOrder = orderService.updateStatus(paymentOrder.orderId(), "", "pending");

        Map<String, Object> payload = Map.of(
                "orderId", order.getId(),
                "razorpayOrderId", paymentOrder.orderId(),
                "amount", paymentOrder.totalAmount(),
                "gstAmount", paymentOrder.gstAmount(),
                "status", "PENDING"
        );
        return ResponseEntity.ok(new ApiResponse<>("Order created", payload));
    }

    @PostMapping("/payments/create-order")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createPaymentOrder(@RequestBody Map<String, Object> payload) {
        BigDecimal amount = new BigDecimal(payload.getOrDefault("amount", "0").toString());
        String receipt = payload.getOrDefault("receipt", "repair-order").toString();
        RazorpayService.PaymentOrderResult result = razorpayService.createOrder(amount, receipt);
        return ResponseEntity.ok(new ApiResponse<>("Payment order created", Map.of(
                "orderId", result.orderId(),
                "amount", result.amountInPaise(),
                "currency", "INR"
        )));
    }

    @PostMapping("/payments/confirm")
    public ResponseEntity<ApiResponse<Map<String, Object>>> confirmPayment(@RequestBody PaymentCallbackRequest callback) {
        Order order = orderService.updateStatus(callback.getRazorpayOrderId(), callback.getRazorpayPaymentId(), callback.getStatus());
        Map<String, Object> data = Map.of(
                "orderId", order.getId(),
                "status", order.getStatus().name(),
                "paymentId", order.getPaymentId()
        );
        return ResponseEntity.ok(new ApiResponse<>("Payment confirmed", data));
    }
}
