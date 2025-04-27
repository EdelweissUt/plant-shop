package com.plant.plantshopbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    String productId;
    String orderItemId;
    int rating;
    String comment;
    String parentId; // Optional
}
