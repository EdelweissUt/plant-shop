package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.NotificationRequest;
import com.plant.plantshopbe.entity.Notification;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.UserNotification;
import com.plant.plantshopbe.repository.UserNotificationRepository;
import com.plant.plantshopbe.service.NotificationService;
import com.plant.plantshopbe.service.UserNotificationService;
import com.plant.plantshopbe.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {

    NotificationService notificationService;
    UserNotificationRepository userNotificationRepository;
    UserService userService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest request) {
        var noti = notificationService.createNotification(request);
        return ResponseEntity.ok(noti.getId());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PostMapping("/send/{notificationId}")
    public ResponseEntity<String> sendToUsers(
            @PathVariable String notificationId,
            @RequestBody List<String> userIds) {
        notificationService.sendNotificationToUsers(notificationId, userIds);
        return ResponseEntity.ok("Sent successfully.");
    }
    // Fetch notifications for the current user with notification details
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Notification>> getNotificationsForCurrentUser() {
        User user = userService.getCurrentUser();  // Fetch current user
        List<UserNotification> userNotifications = userNotificationRepository.findByUser(user);  // Find notifications for this user

        // Map UserNotification to Notification (you can map it to a DTO for better control)
        List<Notification> notifications = userNotifications.stream()
                .map(userNotification -> userNotification.getNotification())  // Extract Notification from UserNotification
                .collect(Collectors.toList());

        return ResponseEntity.ok(notifications);  // Return list of notifications
    }

    // Get all notifications (for all users)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/isRead")
    public void markAsRead(@PathVariable String userNotificationId) {
        User user = userService.getCurrentUser();
        UserNotification userNotification = userNotificationRepository.findById(userNotificationId)
                .orElseThrow(() -> new RuntimeException("UserNotification not found"));

        userNotification.setRead(true);
        userNotificationRepository.save(userNotification);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/mark-all-read")
    public ResponseEntity<String> markAllAsRead() {
        // Get the current user
        User user = userService.getCurrentUser();

        // Find all UserNotification entries for the current user where isRead is false
        List<UserNotification> userNotifications = userNotificationRepository.findByUserAndIsReadFalse(user);

        if (userNotifications.isEmpty()) {
            return ResponseEntity.ok("No unread notifications.");
        }

        // Mark each notification as read and save the changes
        userNotifications.forEach(userNotification -> {
            userNotification.setRead(true);
            userNotificationRepository.save(userNotification);
        });

        return ResponseEntity.ok("All notifications marked as read.");
    }

}