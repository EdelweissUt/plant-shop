package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.Cart;
import com.plant.plantshopbe.entity.CartItem;
import com.plant.plantshopbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart> findByUser(User user);
}
