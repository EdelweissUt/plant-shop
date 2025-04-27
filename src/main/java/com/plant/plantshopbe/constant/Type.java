package com.plant.plantshopbe.constant;

public class Type {
    // Enum OrderStatus
    public enum OrderStatus {
        PENDING, CONFIRMED,SHIPPED, DELIVERED, COMPLETED, CANCELLED;
    }

    // Enum PaymentMethod
    public enum PaymentMethod {
        COD, PAYOS;
    }

    // Enum PaymentStatus
    public enum PaymentStatus {
        PENDING, PAID, FAILED, REFUNDED;
    }
    public enum EntityType {
        REVIEW, PRODUCT, USER, CATEGORY,BANNER,DISCOUNT;
    }
    public enum RefundStatus {
        PENDING, COMPLETED, FAILED;
    }
    public enum NotificationType {
        ORDER_UPDATE,
        PROMOTION,
        SYSTEM_ALERT;
    }
    public enum CustomerTypeEnum {
        NORMAL, VIP, PREMIUM
    }
    public enum DiscountType {
        FIXED,      // Giảm số tiền cố định
        PERCENTAGE  // Giảm theo %
    }
    public enum TokenType {
        VERIFY_EMAIL,
        RESET_PASSWORD
    }


}
