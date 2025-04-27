package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, String> {
    List<OrderHistory> findByOrderIdOrderByChangedAtDesc(String orderId);
}