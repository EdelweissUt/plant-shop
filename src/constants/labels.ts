import {CustomerType, DiscountType, NotificationType, OrderStatus, PaymentMethod, PaymentStatus} from "./enum";

export const OrderStatusLabel: Record<OrderStatus, string> = {
    PENDING: 'Chờ xác nhận',
    CONFIRMED: 'Đã xác nhận',
    SHIPPED: 'Đang vận chuyển',
    DELIVERED: 'Đã giao hàng',
    COMPLETED: 'Hoàn tất',
    CANCELLED: 'Đã hủy',
};

export const PaymentMethodLabel: Record<PaymentMethod, string> = {
    COD: 'Thanh toán khi nhận hàng',
    PAYOS: 'Thanh toán online (PayOS)',
};

export const PaymentStatusLabel: Record<PaymentStatus, string> = {
    PENDING: 'Chờ thanh toán',
    PAID: 'Đã thanh toán',
    FAILED: 'Thất bại',
    REFUNDED: 'Hoàn tiền',
};

export const NotificationTypeLabel: Record<NotificationType, string> = {
    ORDER_UPDATE: 'Cập nhật đơn hàng',
    PROMOTION: 'Khuyến mãi',
    SYSTEM_ALERT: 'Thông báo hệ thống',
};

export const CustomerTypeLabel: Record<CustomerType, string> = {
    NORMAL: 'Thường',
    VIP: 'VIP',
    PREMIUM: 'Thành viên cao cấp',
};

export const DiscountTypeLabel: Record<DiscountType, string> = {
    FIXED: 'Giảm cố định',
    PERCENTAGE: 'Giảm theo phần trăm',
};
