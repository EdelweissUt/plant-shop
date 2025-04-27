package com.plant.plantshopbe.service;

import com.plant.plantshopbe.dto.request.NotificationRequest;
import com.plant.plantshopbe.dto.response.UserNotificationResponse;
import com.plant.plantshopbe.entity.Notification;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.UserNotification;
import com.plant.plantshopbe.mapper.NotificationMapper;
import com.plant.plantshopbe.repository.NotificationRepository;
import com.plant.plantshopbe.repository.UserNotificationRepository;
import com.plant.plantshopbe.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {

     NotificationRepository notificationRepo;
     UserNotificationRepository userNotificationRepo;
     UserRepository userRepo;
     SimpMessagingTemplate messagingTemplate;
     NotificationMapper mapper;
     EmailService emailService;
    public Notification createNotification(NotificationRequest request) {
        Notification notification = mapper.toEntity(request);
        return notificationRepo.save(notification);
    }

    public void sendNotificationToUsers(String notificationId, List<String> userIds) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        List<User> users = userRepo.findAllById(userIds);
        List<UserNotification> userNotifications = new ArrayList<>();

        for (User user : users) {
            UserNotification un = UserNotification.builder()
                    .notification(notification)
                    .user(user)
                    .isRead(false)
                    .build();
            userNotifications.add(un);
        }

        List<UserNotification> saved = userNotificationRepo.saveAll(userNotifications);

        for (UserNotification un : saved) {
            UserNotificationResponse response = mapper.toUserNotificationResponse(un);
            messagingTemplate.convertAndSend("/topic/notifications/" + un.getUser().getId(), response);
            // Gửi email
            String email = un.getUser().getEmail();
            if (email != null && !email.isBlank()) {
                emailService.sendNotificationEmail(
                        email,
                        notification.getTitle(),
                        "<p>" + notification.getMessage() + "</p>"
                );
            }        }

    }

    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }
}