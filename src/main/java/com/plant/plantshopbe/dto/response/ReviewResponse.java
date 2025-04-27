package com.plant.plantshopbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    String id;
    String userId;
    String username;
    String avatarUrl;
    int rating;
    String comment;
    List<String> mediaUrls;
    List<ReviewResponse> replies;
    long likeCount;
    boolean likedByCurrentUser;
}