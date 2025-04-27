package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.Wishlist;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.ProductRepository;
import com.plant.plantshopbe.repository.WishlistRepository;
import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistService {
    WishlistRepository wishlistRepository;
    ProductRepository productRepository;
    UserService userService;

    public Wishlist addToWishlist(String productId) {
        User user = userService.getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check tồn tại
        wishlistRepository.findByUserIdAndProductId(user.getId(), productId)
                .ifPresent(w -> { throw new AppException(ErrorCode.ALREADY_EXIST); });

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        return wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(String productId) {
        User user = userService.getCurrentUser();
        wishlistRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    public List<Wishlist> getUserWishlist() {
        User user = userService.getCurrentUser();
        return wishlistRepository.findAllByUserId(user.getId());
    }
}