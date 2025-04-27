package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.entity.Wishlist;
import com.plant.plantshopbe.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class WishlistController {

    WishlistService wishlistService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{productId}")
    ApiResponse<Wishlist> addToWishlist(@PathVariable("productId") String productId) {
        return ApiResponse.<Wishlist>builder()
                .result(wishlistService.addToWishlist(productId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{productId}")
    ApiResponse<String> removeFromWishlist(@PathVariable("productId") String productId) {
        wishlistService.removeFromWishlist(productId);
        return ApiResponse.<String>builder()
                .result("Removed from wishlist")
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    ApiResponse<List<Wishlist>> getUserWishlist() {
        return ApiResponse.<List<Wishlist>>builder()
                .result(wishlistService.getUserWishlist())
                .build();
    }
}