package com.plant.plantshopbe.service;


import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.NotificationRequest;
import com.plant.plantshopbe.entity.*;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.DiscountCodeRepository;
import com.plant.plantshopbe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountCodeService {
    DiscountCodeRepository discountCodeRepository ;
    UserRepository userRepository;
    NotificationService notificationService;
    public DiscountCode createDiscountCode(DiscountCode discountCode) {
        DiscountCode saveDiscountCode = discountCodeRepository.save(discountCode);
        // Gửi thông báo nếu có CustomerType
        if (saveDiscountCode.getCustomerType() != null) {
            sendPromotionNotification(saveDiscountCode);
        }
        return saveDiscountCode;
    }
    private void sendPromotionNotification(DiscountCode discountCode) {
        CustomerType customerType = discountCode.getCustomerType();
        List<User> users = userRepository.findByCustomerType(customerType);

        String message = String.format(
                "🎁 Mã khuyến mãi: <b>%s</b><br>" +
                        "Loại: %s<br>" +
                        "Giá trị: %s<br>" +
                        "Áp dụng cho đơn tối thiểu: %s<br>" +
                        "Hiệu lực: %s - %s",
                discountCode.getCode(),
                discountCode.getDiscountType().name(),
                discountCode.getDiscountValue().toPlainString(),
                discountCode.getMinOrderValue().toPlainString(),
                discountCode.getStartDate().toLocalDate(),
                discountCode.getEndDate().toLocalDate()
        );

        NotificationRequest notification = NotificationRequest.builder()
                .title("Khuyến mãi dành cho bạn!")
                .message(message)
                .type(Type.NotificationType.PROMOTION)
                .build();

        Notification created = notificationService.createNotification(notification);

        List<String> userIds = users.stream()
                .map(User::getId)
                .toList();

        notificationService.sendNotificationToUsers(created.getId(), userIds);
    }


    public DiscountCode updateDiscountCode(String discountCodeId, DiscountCode discountCode) {
        try {
            // Kiểm tra nếu tài khoản ngân hàng không tồn tại
            DiscountCode existingDiscountCode = discountCodeRepository.findById(discountCodeId).orElseThrow(()-> new AppException(ErrorCode.BANK_NOT_EXITED));
            discountCode.setId(discountCodeId);
            discountCodeRepository.save(discountCode);

            // Trả về tài khoản ngân hàng đã được cập nhật
            return discountCode;
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            System.err.println("Error updating bank account: " + e.getMessage());
            throw new RuntimeException("Failed to update bank account.", e);
        }
    }

    public DiscountCode getDiscountCode(String discountCodeId) {
        return discountCodeRepository.findById(discountCodeId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public void deleteDiscountCode(String discountCodeId) {
        discountCodeRepository.deleteById(discountCodeId);
    }

    public List<DiscountCode> getDiscountCodes() {
        return discountCodeRepository.findAll();
    }
}
