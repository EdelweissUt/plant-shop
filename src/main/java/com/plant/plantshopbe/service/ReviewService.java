package com.plant.plantshopbe.service;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.ReviewRequest;
import com.plant.plantshopbe.dto.response.ReviewResponse;
import com.plant.plantshopbe.entity.*;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.ReviewMapper;
import com.plant.plantshopbe.repository.*;
import com.plant.plantshopbe.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReviewService {
    ReviewRepository reviewRepository;
    ReviewLikeRepository likeRepo;
    OrderItemRepository orderItemRepository;
    MediaService mediaService;
    CloudinaryService cloudinaryService;
    UserRepository userRepository;
    UserService userService;
    ProductRepository productRepository;
    ReviewMapper reviewMapper;

    public ReviewResponse createReview(ReviewRequest request, List<MultipartFile> mediaFiles) {
        User user = getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        OrderItem orderItem = null;
        if (request.getOrderItemId() != null) {
            orderItem = orderItemRepository.findById(request.getOrderItemId())
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));
        }

        Review parent = null;
        if (request.getParentId() != null) {
            parent = reviewRepository.findById(request.getParentId())
                    .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .orderItem(orderItem)
                .rating(request.getRating())
                .comment(request.getComment())
                .parent(parent)
                .build();
        review = reviewRepository.save(review);

        // Upload media
        uploadFiles(Type.EntityType.REVIEW.name(), review.getId(), mediaFiles);

        return toResponse(review);
    }

    public List<ReviewResponse> getReviewsByProduct(String productId) {
        List<Review> reviews = reviewRepository.findByProductIdAndParentIsNull(productId);
        return reviews.stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewsByOrderItem(String orderItemId) {
        List<Review> reviews = reviewRepository.findByOrderItemId(orderItemId);
        return reviews.stream().map(this::toResponse).toList();
    }

    private ReviewResponse toResponse(Review review) {
        List<String> mediaUrls = mediaService.getMedias(Type.EntityType.REVIEW.name(), review.getId()).stream()
                .map(Media::getMediaUrl).toList();
        String avatar = mediaService.getMedias("USER", review.getUser().getId()).stream()
                .findFirst().map(Media::getMediaUrl).orElse(null);

        List<ReviewResponse> replies = reviewRepository.findByParentId(review.getId()).stream()
                .map(this::toResponse).toList();

        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .username(review.getUser().getFullName())
                .avatarUrl(avatar)
                .comment(review.getComment())
                .rating(review.getRating())
                .likeCount(review.getLikeCount())
                .mediaUrls(mediaUrls)
                .replies(replies)
                .build();
    }

    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    private void uploadFiles(String entityType, String entityId, List<MultipartFile> mediaFiles) {
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            for (MultipartFile file : mediaFiles) {
                var response = cloudinaryService.uploadFile(file, FileUploadUtil.getFileName("review"), "plant");
                Media media = Media.builder()
                        .mediaUrl(response.getUrl())
                        .publicId(response.getPublicId())
                        .mediaType(file.getContentType().contains("image") ? "Image" : "Video")
                        .mediaFormat(file.getContentType())
                        .entityType(entityType)
                        .entityId(entityId)
                        .build();
                mediaService.createMedia(media);
            }
        }
    }

    public ReviewResponse getReviewById(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));
        return toResponse(review);
    }

    @Transactional
    public void toggleLikeReview(String reviewId) {
        User user = getCurrentUser();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));

        Optional<ReviewLike> optionalLike = likeRepo.findByReviewIdAndUserId(reviewId, user.getId());
        if (optionalLike.isPresent()) {
            ReviewLike like = optionalLike.get();
            like.setLiked(!like.isLiked());
            likeRepo.save(like);
            review.setLikeCount(review.getLikeCount() + (like.isLiked() ? 1 : -1));
        } else {
            ReviewLike newLike = ReviewLike.builder()
                    .review(review)
                    .user(user)
                    .liked(true)
                    .build();
            likeRepo.save(newLike);
            review.setLikeCount(review.getLikeCount() + 1);
        }

        reviewRepository.save(review);
    }

    public Long getLikeCount(String reviewId) {
        return likeRepo.countByReviewIdAndLikedTrue(reviewId);
    }

    public ReviewResponse updateReview(String reviewId, ReviewRequest request, List<MultipartFile> mediaFiles) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        List<String> urlMedias = mediaService.getMedias(Type.EntityType.REVIEW.name(),reviewId).stream().map(Media::getMediaUrl).toList();
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            mediaService.deleteMedia(Type.EntityType.REVIEW.name(), review.getId());
            uploadFiles(Type.EntityType.REVIEW.name(),reviewId,mediaFiles);
            urlMedias = mediaService.getMedias(Type.EntityType.REVIEW.name(),reviewId).stream().map(Media::getMediaUrl).toList();
            
        }

        reviewRepository.save(review);
        return reviewMapper.toResponse(review);
    }

    @Transactional
    public void deleteReview(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));

        User currentUser = userService.getCurrentUser();
        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Xóa media liên quan
        mediaService.deleteMedia(Type.EntityType.REVIEW.name(), review.getId());

        // Xóa like/unlike liên quan
        likeRepo.deleteByReviewId(review.getId());

        // Xóa replies nếu có (nếu bạn dùng review có replies)
        reviewRepository.deleteByParentId(review.getId());

        // Xóa chính đánh giá
        reviewRepository.delete(review);
    }

    public boolean isReviewLikedByCurrentUser(String reviewId) {
        String userId = userService.getCurrentUser().getId();
        return likeRepo.existsByReviewIdAndUserIdAndLikedTrue(reviewId, userId);
    }

}
