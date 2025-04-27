package com.plant.plantshopbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;

    LocalDateTime issuedDate;

    String taxCode;

    String companyName;

    String companyAddress;
}