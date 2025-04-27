package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.NotificationRequest;
import com.plant.plantshopbe.dto.response.NotificationResponse;
import com.plant.plantshopbe.dto.response.UserNotificationResponse;
import com.plant.plantshopbe.entity.Notification;
import com.plant.plantshopbe.entity.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationRequest dto);

    @Mapping(target = "read", expression = "java(false)")
    NotificationResponse toResponse(Notification notification);

    @Mapping(source = "notification", target = "notification")
    UserNotificationResponse toUserNotificationResponse(UserNotification entity);
}