import { OrderStatus, PaymentStatus, NotificationType } from './enum';

export const OrderStatusColor: Record<OrderStatus, string> = {
    PENDING: 'gray',
    CONFIRMED: 'blue',
    SHIPPED: 'amber',
    DELIVERED: 'green',
    COMPLETED: 'teal',
    CANCELLED: 'red',
};

export const PaymentStatusColor: Record<PaymentStatus, string> = {
    PENDING: 'gray',
    PAID: 'green',
    FAILED: 'red',
    REFUNDED: 'blue',
};

export const NotificationTypeColor: Record<NotificationType, string> = {
    ORDER_UPDATE: 'blue',
    PROMOTION: 'pink',
    SYSTEM_ALERT: 'red',
};
