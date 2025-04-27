package com.plant.plantshopbe.entity;

import com.plant.plantshopbe.constant.Type;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class RefundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
     User user;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
     BankAccount bankAccount;

    @Column(nullable = false)
     BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
     Type.RefundStatus status = Type.RefundStatus.PENDING;

    @CreationTimestamp
     LocalDateTime createdAt ;





}