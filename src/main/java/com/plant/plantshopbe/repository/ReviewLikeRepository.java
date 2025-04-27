package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, String> {
    Optional<ReviewLike> findByReviewIdAndUserId(String reviewId, String userId);
    Long countByReviewIdAndLikedTrue(String reviewId);

    void deleteByReviewId(String id);

    boolean existsByReviewIdAndUserId(String reviewId, String id);

    long countByReviewId(String reviewId);

    boolean existsByReviewIdAndUserIdAndLikedTrue(String reviewId, String userId);
}

