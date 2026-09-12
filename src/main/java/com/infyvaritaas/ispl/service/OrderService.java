package com.infyvaritaas.ispl.service;

import com.infyvaritaas.ispl.domain.Device;
import com.infyvaritaas.ispl.domain.Order;
import com.infyvaritaas.ispl.domain.OrderItem;
import com.infyvaritaas.ispl.domain.OrderStatus;
import com.infyvaritaas.ispl.domain.RepairService;
import com.infyvaritaas.ispl.domain.User;
import com.infyvaritaas.ispl.exception.BadRequestException;
import com.infyvaritaas.ispl.exception.ResourceNotFoundException;
import com.infyvaritaas.ispl.repository.DeviceRepository;
import com.infyvaritaas.ispl.repository.OrderRepository;
import com.infyvaritaas.ispl.repository.RepairServiceRepository;
import com.infyvaritaas.ispl.repository.UserRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final RepairServiceRepository repairServiceRepository;
    private final RazorpayService razorpayService;

    public OrderService(OrderRepository orderRepository,
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            RepairServiceRepository repairServiceRepository,
            RazorpayService razorpayService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.repairServiceRepository = repairServiceRepository;
        this.razorpayService = razorpayService;
    }

    @Transactional
    public Order createOrder(Long userId, Long deviceId, Long serviceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id " + deviceId));
        RepairService service = repairServiceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id " + serviceId));

        if (!service.getDevice().getId().equals(device.getId())) {
            throw new BadRequestException("Selected service does not belong to chosen device.");
        }

        BigDecimal subtotal = service.getPrice();
        BigDecimal gstAmount = subtotal.multiply(razorpayService.isConfigured()
                ? new java.math.BigDecimal("0.18")
                : new java.math.BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = subtotal.add(gstAmount).setScale(2, RoundingMode.HALF_UP);

        Order order = new Order();
        order.setUser(user);
        order.setDevice(device);
        order.setService(service);
        order.setAmount(subtotal);
        order.setGstAmount(gstAmount);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setService(service);
        item.setQuantity(1);
        item.setUnitPrice(service.getPrice());
        item.setTotalPrice(service.getPrice());
        order.getItems().add(item);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Order updateStatus(String razorpayOrderId, String paymentId, String status) {
        Optional<Order> orderOptional = orderRepository.findByRazorpayOrderId(razorpayOrderId);
        if (orderOptional.isEmpty()) {
            throw new ResourceNotFoundException("Order not found for razorpay order id " + razorpayOrderId);
        }

        Order order = orderOptional.get();
        order.setPaymentId(paymentId);
        order.setStatus("paid".equalsIgnoreCase(status) ? OrderStatus.PAID : OrderStatus.PENDING);
        return orderRepository.save(order);
    }
}
