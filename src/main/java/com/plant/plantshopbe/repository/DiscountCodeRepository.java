package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.BankAccount;
import com.plant.plantshopbe.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode,String> {
    Optional<DiscountCode> findByCode(String discountCode);
}
