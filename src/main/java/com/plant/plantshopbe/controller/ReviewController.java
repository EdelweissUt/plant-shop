package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.ReviewRequest;
import com.plant.plantshopbe.dto.response.ReviewResponse;
import com.plant.plantshopbe.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    // 1. Tạo đánh giá mới (có thể là đánh giá hoặc reply)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ApiResponse<ReviewResponse> createReview(
            @RequestPart("review") ReviewRequest request,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
    ) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.createReview(request, mediaFiles))
                .build();
    }

    // 2. Lấy tất cả đánh giá theo sản phẩm
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProduct(@PathVariable String productId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsByProduct(productId))
                .build();
    }

    // 3. Lấy đánh giá theo OrderItem (kiểm tra xem người dùng đã đánh giá chưa)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/order-item/{orderItemId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByOrderItem(@PathVariable String orderItemId) {
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsByOrderItem(orderItemId))
                .build();
    }

    // 4. Like hoặc Unlike đánh giá
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{reviewId}/like")
    public ApiResponse<String> likeOrUnlikeReview(@PathVariable String reviewId) {
        reviewService.toggleLikeReview(reviewId);
        return ApiResponse.<String>builder()
                .result("Toggled like status successfully")
                .build();
    }

    // 5. Lấy số lượt like của đánh giá
    @GetMapping("/{reviewId}/likes")
    public ApiResponse<Long> getReviewLikeCount(@PathVariable String reviewId) {
        return ApiResponse.<Long>builder()
                .result(reviewService.getLikeCount(reviewId))
                .build();
    }

    // 6. Lấy đánh giá chi tiết theo ID
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> getReviewById(@PathVariable String reviewId) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.getReviewById(reviewId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable String reviewId,
            @RequestPart("review") ReviewRequest request,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
    ) {
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.updateReview(reviewId, request, mediaFiles))
                .build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{reviewId}")
    public ApiResponse<String> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.<String>builder().result("Xóa đánh giá thành công").build();
    }
    // 7. Kiểm tra người dùng hiện tại đã like review chưa
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{reviewId}/liked")
    public ApiResponse<Boolean> isReviewLikedByCurrentUser(@PathVariable String reviewId) {
        boolean liked = reviewService.isReviewLikedByCurrentUser(reviewId);
        return ApiResponse.<Boolean>builder()
                .result(liked)
                .build();
    }




}
