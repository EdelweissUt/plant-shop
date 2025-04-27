package com.plant.plantshopbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

     String name;

     String description;
    
     BigDecimal originalPrice;

     BigDecimal discountPrice;

     Integer stockQuantity  =   0;

     Integer soldQuantity = 0;

     String size;

    @ManyToOne
    @JoinColumn(name = "category_id")
     Category category;

     Boolean isHidden = false;
     Boolean isDeleted = false;

     @CreationTimestamp
     LocalDateTime createdAt;

    @UpdateTimestamp
     LocalDateTime updatedDate;


}
