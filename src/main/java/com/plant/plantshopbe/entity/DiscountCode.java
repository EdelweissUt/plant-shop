package com.plant.plantshopbe.entity;

import com.plant.plantshopbe.constant.Type;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false, length = 50)
    private String code; // Mã giảm giá

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type.DiscountType discountType; // Kiểu giảm giá (FIXED hoặc PERCENTAGE)

    @Column(nullable = false)
    private BigDecimal discountValue; // Giá trị giảm (số tiền hoặc %)

    @Column(nullable = false)
    private BigDecimal minOrderValue; // Giá trị đơn hàng tối thiểu

    private BigDecimal maxDiscount; // Giảm tối đa nếu là %

    @Column(nullable = false)
    private Integer quantity; // Số lượng mã có sẵn

    @Column(nullable = false)
    private Boolean isActive = true; // Trạng thái mã

    @Column(nullable = false)
    private LocalDateTime startDate; // Ngày bắt đầu

    @Column(nullable = false)
    private LocalDateTime endDate; // Ngày kết thúc

    @CreationTimestamp
    private LocalDateTime createdAt; // Ngày tạo

    @ManyToOne
    @JoinColumn(name = "customer_type_id")
    private CustomerType customerType; // Loại khách hàng áp dụng

    public boolean isValidForUse(BigDecimal orderValue,User user) {
        return isActive
                && LocalDateTime.now().isAfter(startDate)
                && LocalDateTime.now().isBefore(endDate)
                && orderValue.compareTo(minOrderValue) >= 0
                && quantity > 0
                && (customerType == null || customerType.equals(user.getCustomerType()));

    }

    public BigDecimal applyDiscount(BigDecimal orderValue) {
        BigDecimal discount = BigDecimal.ZERO;

        if (discountType == Type.DiscountType.FIXED) {
            // Giảm giá số tiền cố định
            discount = discountValue;
        } else if (discountType == Type.DiscountType.PERCENTAGE) {
            // Giảm theo % với mức giảm tối đa
            discount = orderValue.multiply(discountValue.divide(BigDecimal.valueOf(100)));
            if (maxDiscount != null && discount.compareTo(maxDiscount) > 0) {
                discount = maxDiscount;
            }
        }

        return orderValue.subtract(discount.max(BigDecimal.ZERO)); // Không cho giảm giá âm
    }
}
