package com.plant.plantshopbe.dto.response;

import com.plant.plantshopbe.constant.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
     String id;
    String userId;
    BigDecimal totalPrice;
     BigDecimal shippingFee;
     Type.OrderStatus status;
     Type.PaymentStatus paymentStatus;
     Type.PaymentMethod paymentMethod;
     ShippingAddressRespose shippingAddress;
    List<OrderItemResponse> items;

}
