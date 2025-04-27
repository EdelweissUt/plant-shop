package com.plant.plantshopbe.dto.response;

import com.plant.plantshopbe.constant.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    String id;
    String title;
    String message;
    Type.NotificationType type;
    LocalDateTime createdAt;
    boolean read;
}
