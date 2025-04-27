package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.response.CartItemResponse;
import com.plant.plantshopbe.dto.response.CartResponse;
import com.plant.plantshopbe.entity.Cart;
import com.plant.plantshopbe.entity.CartItem;
import com.plant.plantshopbe.entity.Product;
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
public class CartMapper {

    MediaService mediaService;
    @Autowired
    public CartMapper(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    public CartResponse toCartResponse(Cart cart) {
        if (cart == null) return null;

        List<CartItemResponse> items = cart.getItems() != null
                ? cart.getItems().stream()
                .filter(Objects::nonNull)
                .map(this::toCartItemResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .items(items)
                .build();
    }

    public CartItemResponse toCartItemResponse(CartItem item) {
        if (item == null || item.getProduct() == null) return null;
        Product product = item.getProduct();

        List<String> imageUrls = mediaService.getMedias("Product", product.getId()) != null
                ? mediaService.getMedias("Product", product.getId()).stream()
                .filter(Objects::nonNull)
                .map(media -> media.getMediaUrl())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return CartItemResponse.builder()
                .id(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .productImages(imageUrls)
                .quantity(item.getQuantity())
                .updatedAt(item.getUpdatedAt() != null ? item.getUpdatedAt().toString() : null)
                .build();
    }
}