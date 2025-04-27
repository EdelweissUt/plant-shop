package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.Order;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendNotificationEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true nếu bạn dùng HTML

            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
    public void sendOrderConfirmationEmail(String to, Order order) {
        String subject = "Xác nhận đơn hàng #" + order.getId();
        String content = "<h3>Đơn hàng của bạn đã được đặt thành công!</h3>"
                + "<p>Mã đơn hàng: " + order.getId() + "</p>"
                + "<p>Tổng tiền: " + order.getTotalPrice() + "</p>"
                + "<p>Trạng thái: " + order.getStatus() + "</p>";

        sendNotificationEmail(to, subject, content);
    }

    public void sendOrderStatusEmail(String to, String message) {
        String subject = "Cập nhật trạng thái đơn hàng";
        String content = "<p>" + message + "</p>";
        sendNotificationEmail(to, subject, content);
    }

}
