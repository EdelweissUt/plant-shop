package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.OrderHistory;
import com.plant.plantshopbe.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {
    List<PaymentHistory> findByOrderIdOrderByChangedAtDesc(String orderId);
}