export enum OrderStatus {
    PENDING = 'PENDING',
    CONFIRMED = 'CONFIRMED',
    SHIPPED = 'SHIPPED',
    DELIVERED = 'DELIVERED',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED',
}

export enum PaymentMethod {
    COD = 'COD',
    PAYOS = 'PAYOS',
}

export enum PaymentStatus {
    PENDING = 'PENDING',
    PAID = 'PAID',
    FAILED = 'FAILED',
    REFUNDED = 'REFUNDED',
}

export enum EntityType {
    REVIEW = 'REVIEW',
    PRODUCT = 'PRODUCT',
    USER = 'USER',
    CATEGORY = 'CATEGORY',
    BANNER = 'BANNER',
    DISCOUNT = 'DISCOUNT',
}

export enum RefundStatus {
    PENDING = 'PENDING',
    COMPLETED = 'COMPLETED',
    FAILED = 'FAILED',
}

export enum NotificationType {
    ORDER_UPDATE = 'ORDER_UPDATE',
    PROMOTION = 'PROMOTION',
    SYSTEM_ALERT = 'SYSTEM_ALERT',
}

export enum CustomerType {
    NORMAL = 'NORMAL',
    VIP = 'VIP',
    PREMIUM = 'PREMIUM',
}

export enum DiscountType {
    FIXED = 'FIXED',
    PERCENTAGE = 'PERCENTAGE',
}

export enum TokenType {
    VERIFY_EMAIL = 'VERIFY_EMAIL',
    RESET_PASSWORD = 'RESET_PASSWORD',
}
