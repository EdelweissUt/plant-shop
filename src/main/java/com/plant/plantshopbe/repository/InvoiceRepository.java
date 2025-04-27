package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.Invoice;
import com.plant.plantshopbe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    Optional<Invoice> findByOrderId(String orderId);
    Optional<Invoice> findByOrder(Order order);
    long countByIssuedDateBetween(LocalDateTime start, LocalDateTime end);


}