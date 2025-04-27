package com.plant.plantshopbe.service;

import com.plant.plantshopbe.dto.request.CartRequest;
import com.plant.plantshopbe.dto.response.CartResponse;
import com.plant.plantshopbe.entity.Cart;
import com.plant.plantshopbe.entity.CartItem;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.CartMapper;
import com.plant.plantshopbe.repository.CartItemRepository;
import com.plant.plantshopbe.repository.CartRepository;
import com.plant.plantshopbe.repository.ProductRepository;
import com.plant.plantshopbe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    CartMapper cartMapper;
    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = Cart.builder().user(user).build();
            return cartRepository.save(cart);
        });
    }

    public CartResponse getCartByUserId() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        return cartMapper.toCartResponse(cart);
    }

    @Transactional
    public CartResponse addToCart(CartRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check quantity range
        if (request.getQuantity() <= 0 || request.getQuantity() > 100) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        // Ensure cart items list is initialized
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        int totalQuantity = request.getQuantity() + (existingItem != null ? existingItem.getQuantity() : 0);
        if (product.getStockQuantity() < totalQuantity) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        if (existingItem != null) {
            existingItem.setQuantity(totalQuantity);
            existingItem.setProduct(product);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.getItems().add(newItem);
        }

        Cart saved = cartRepository.save(cart);
        return cartMapper.toCartResponse(saved);
    }



    @Transactional
    public CartResponse  updateItem(String productId, int quantity) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        cart.getItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });

        Cart updated = cartRepository.save(cart);
        return cartMapper.toCartResponse(updated);
    }

    @Transactional
    public CartResponse removeItem(String productId) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        Cart updated = cartRepository.save(cart);
        return cartMapper.toCartResponse(updated);
    }


    @Transactional
    public void clearCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}