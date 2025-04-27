package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.DiscountCode;
import com.plant.plantshopbe.entity.DiscountUsage;
import com.plant.plantshopbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountUsageRepository extends JpaRepository<DiscountUsage, String> {
    boolean existsByUserAndDiscountCode(User user, DiscountCode code);

    Optional<DiscountUsage> findByOrderId(String orderId);
}