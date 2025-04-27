package com.plant.plantshopbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
     User user;
    @OneToMany(mappedBy = "shippingAddress")
     Set<Order> orders;

    @Column(nullable = false)
     String recipientName;

    @Column(nullable = false)
     String phone;

    @Column(nullable = false)
    String address; // Địa chỉ chi tiết: số nhà, tên đường

    @Column(nullable = false)
    String ward; // Phường/Xã

    @Column(nullable = false)
    String district; // Quận/Huyện

    @Column(nullable = false)
    String province; // Tỉnh/Thành phố
}