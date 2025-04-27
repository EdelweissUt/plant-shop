package com.plant.plantshopbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;


    String email;


    String fullName;

    String password;

    LocalDate dob;
    String phone;

    Boolean isVerified;
    Boolean isBlocked ;
    Boolean isDeleted ;

    @CreationTimestamp
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_type_id")  // Cho phép null
    CustomerType customerType;


    BigDecimal points = BigDecimal.ZERO;  // Tích điểm khách hàng

    @ManyToMany
    Set<Role> roles;

   /* // Tự động đặt loại khách hàng mặc định là NORMAL khi tạo User
    @PrePersist
    public void setDefaultCustomerType() {
        if (this.customerType == null) {
            this.customerType = new CustomerType();
            this.customerType.setTypeName(Type.CustomerTypeEnum.NORMAL);
            this.customerType.setPointMultiplier(BigDecimal.valueOf(1.0));
        }
    }*/

    // Hàm tính điểm thưởng dựa trên số tiền chi tiêu
    /*public void addPoints(BigDecimal totalSpent) {
        if (customerType != null) {
            this.points = this.points.add(totalSpent.multiply(customerType.getPointMultiplier()));
        }
    }*/
}
