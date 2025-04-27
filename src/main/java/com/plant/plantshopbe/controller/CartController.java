package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.CartRequest;
import com.plant.plantshopbe.dto.response.CartResponse;
import com.plant.plantshopbe.mapper.CartMapper;
import com.plant.plantshopbe.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartController {

    CartService cartService;
    CartMapper cartMapper;
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getCartByUserId())
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public ApiResponse<CartResponse> addToCart(@RequestBody CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addToCart(request))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ApiResponse<CartResponse> updateCartItem(@RequestBody CartRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateItem(request.getProductId(), request.getQuantity()))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/remove/{productId}")
    public ApiResponse<CartResponse> removeItem(@PathVariable String productId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeItem(productId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart() {
        cartService.clearCart();
        return ApiResponse.<String>builder()
                .result("Cart cleared successfully")
                .build();
    }
}