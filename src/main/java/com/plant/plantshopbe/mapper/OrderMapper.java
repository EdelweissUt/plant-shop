package com.plant.plantshopbe.mapper;


import com.plant.plantshopbe.dto.response.OrderItemResponse;
import com.plant.plantshopbe.dto.response.OrderResponse;
import com.plant.plantshopbe.dto.response.ShippingAddressRespose;
import com.plant.plantshopbe.entity.*;
import com.plant.plantshopbe.service.MediaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderMapper {
    MediaService mediaService;
    ShippingAddressMapper shippingAddressMapper;

    @Autowired
    public OrderMapper(MediaService mediaService, ShippingAddressMapper shippingAddressMapper) {
        this.mediaService = mediaService;
        this.shippingAddressMapper = shippingAddressMapper;
    }

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) return null;

        List<OrderItemResponse> items = order.getItems() != null
                ? order.getItems().stream()
                .filter(Objects::nonNull)
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();
        ShippingAddressRespose shippingAddressRespose = null;
        if (order.getShippingAddress() != null) {
            shippingAddressRespose = shippingAddressMapper.toShippingAddressResponse(order.getShippingAddress());

        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .totalPrice(order.getTotalPrice())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .status(order.getStatus())
                .shippingAddress(shippingAddressRespose)
                .shippingFee(order.getShippingFee())
                .items(items)
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem item) {
        if (item == null || item.getProduct() == null) return null;

        Product product = item.getProduct();

        List<String> imageUrls = mediaService.getMedias("Product", product.getId()) != null
                ? mediaService.getMedias("Product", product.getId()).stream()
                .filter(Objects::nonNull)
                .map(media -> media.getMediaUrl())
                .collect(Collectors.toList())
                : Collections.emptyList();

        String firstImageUrl = imageUrls.isEmpty() ? null : imageUrls.get(0);


        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productImages(firstImageUrl)
                .quantity(item.getQuantity())
                .build();
    }


}
