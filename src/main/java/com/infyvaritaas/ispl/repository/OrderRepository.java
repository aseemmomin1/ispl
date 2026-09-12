package com.infyvaritaas.ispl.repository;

import com.infyvaritaas.ispl.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
}
