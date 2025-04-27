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
public class CustomerType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
     Type.CustomerTypeEnum typeName ;

    @Column(nullable = false)
     BigDecimal pointMultiplier =BigDecimal.ZERO;;

    @Column(nullable = false)
     BigDecimal minSpent;

    @CreationTimestamp
     LocalDateTime createdAt;
    
}