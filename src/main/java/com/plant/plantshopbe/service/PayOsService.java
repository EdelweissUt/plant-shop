package com.plant.plantshopbe.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayOsService {
    PayOS payOS;
    // Tạo link thanh toán
    public CheckoutResponseData createPaymentLink(PaymentData request) throws Exception {
        PaymentData paymentData = PaymentData.builder()
                .orderCode(request.getOrderCode())
                .amount(request.getAmount())
                .description(request.getDescription())
                .returnUrl(request.getReturnUrl())
                .cancelUrl(request.getCancelUrl())
                .items(request.getItems())
                .build();

        return payOS.createPaymentLink(paymentData);
    }
    // Lấy thông tin thanh toán
    public PaymentLinkData getPaymentLinkInformation(long orderCode) throws Exception {
        return payOS.getPaymentLinkInformation(orderCode);
    }

    // Hủy link thanh toán
    public PaymentLinkData cancelPaymentLink(long orderCode, String reason) throws Exception {
        return payOS.cancelPaymentLink(orderCode, reason);
    }

    // Xác nhận webhook
    public String confirmWebhook(String webhookUrl) throws Exception {
        return payOS.confirmWebhook(webhookUrl);
    }
    // Xác minh dữ liệu từ webhook
    public WebhookData verifyPaymentWebhookData(Webhook webhookBody) throws Exception {
        return payOS.verifyPaymentWebhookData(webhookBody);
    }
}
