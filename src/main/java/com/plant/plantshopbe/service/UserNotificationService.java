package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.UserNotification;
import com.plant.plantshopbe.repository.UserNotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserNotificationService {
    UserNotificationRepository userNotificationRepo;

    public void markAsRead(String userNotificationId) {
        UserNotification userNotification = userNotificationRepo.findById(userNotificationId)
                .orElseThrow(() -> new RuntimeException("UserNotification not found"));

        userNotification.setRead(true);
        userNotificationRepo.save(userNotification);
    }
}
