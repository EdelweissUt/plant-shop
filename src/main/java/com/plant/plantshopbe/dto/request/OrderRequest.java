package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.entity.ShippingAddress;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
     String userId;
     ShippingAddress shippingAddress;
     List<OrderItemRequest> items;
     String discountCode; // Có thể null
     BigDecimal usedPoints;
     Type.PaymentMethod paymentMethod;
}