package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.service.UserNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-notifications")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserNotificationController {

    UserNotificationService userNotificationService;

    @PutMapping("/{userNotificationId}/read")
    public ResponseEntity<String> markAsRead(@PathVariable String userNotificationId) {
        userNotificationService.markAsRead(userNotificationId);
        return ResponseEntity.ok("Notification marked as read.");
    }
}
