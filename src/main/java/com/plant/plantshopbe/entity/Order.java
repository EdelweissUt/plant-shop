package com.plant.plantshopbe.entity;

import com.plant.plantshopbe.constant.Type;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    long orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
     User user;

    @Column(nullable = false)
     BigDecimal totalPrice;

    @Column(nullable = false)
     BigDecimal finalAmount;

    @Column(nullable = false)
     BigDecimal shippingFee = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id") // Đảm bảo tên cột đúng với DB
     ShippingAddress shippingAddress;

    @Enumerated(EnumType.STRING)
     Type.OrderStatus status = Type.OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
     Type.PaymentMethod paymentMethod = Type.PaymentMethod.COD;

    @Enumerated(EnumType.STRING)
     Type.PaymentStatus paymentStatus = Type.PaymentStatus.PENDING;



    String transactionId;

     Boolean isDeleted = false;

    @Column
    BigDecimal usedPoints = BigDecimal.ZERO; // điểm tích lũy đã sử dụng cho đơn hàng


    @CreationTimestamp
     LocalDateTime createdAt;

     @UpdateTimestamp
     LocalDateTime updatedStatus ;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderHistory> history = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentHistory> paymentHistories = new ArrayList<>();
}

