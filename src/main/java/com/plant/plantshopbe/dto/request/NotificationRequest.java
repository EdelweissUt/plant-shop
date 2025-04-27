package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.constant.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    String title;
    String message;
    Type.NotificationType type;
}
