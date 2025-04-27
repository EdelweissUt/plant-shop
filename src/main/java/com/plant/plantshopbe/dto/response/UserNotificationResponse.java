package com.plant.plantshopbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNotificationResponse {
    String id;
    NotificationResponse notification;
    boolean isRead;
    LocalDateTime receivedA;
}
