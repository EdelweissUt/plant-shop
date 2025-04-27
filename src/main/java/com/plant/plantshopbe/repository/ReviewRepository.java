package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByOrderItemId(String orderItemId);
    List<Review> findByProductId(String productId);
    List<Review> findByOrderItemId(String orderItemId);
    List<Review> findByParentId(String parentId);
    List<Review> findByProductIdAndParentIsNull(String productId);

    void deleteByParentId(String id);
}