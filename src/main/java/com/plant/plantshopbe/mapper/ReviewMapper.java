package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.ReviewRequest;
import com.plant.plantshopbe.dto.response.ReviewResponse;
import com.plant.plantshopbe.entity.Media;
import com.plant.plantshopbe.entity.Review;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.repository.ReviewLikeRepository;
import com.plant.plantshopbe.repository.ReviewRepository;
import com.plant.plantshopbe.service.MediaService;
import com.plant.plantshopbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

   MediaService mediaService;
   UserMapper userMapper;
  ReviewLikeRepository reviewLikeRepository;
     ReviewRepository reviewRepository;
    UserService userService;



    public ReviewResponse toResponse(Review review) {
        User user = review.getUser();
        String reviewId = review.getId();
        String userId = user.getId();
        List<String> urlMedias = mediaService.getMedias(Type.EntityType.REVIEW.name(), reviewId).stream().map(Media::getMediaUrl).toList();

        return ReviewResponse.builder()
                .id(reviewId)
                .userId(userId)
                .username(user.getEmail())
                .avatarUrl(mediaService.getAvatar("USER", userId).getMediaUrl())
                .rating(review.getRating())
                .comment(review.getComment())
                .mediaUrls(urlMedias)
                .replies(reviewRepository.findByParentId(reviewId).stream()
                        .map(this::toResponse)
                        .toList())
                .likeCount(review.getLikeCount())
                .likedByCurrentUser(
                        reviewLikeRepository.existsByReviewIdAndUserId(reviewId, userService.getCurrentUser().getId()))
                .build();
    }

    public Review toEntity(ReviewRequest request) {
        return Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())

                .build();
    }
}
