package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.service.PayOsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.payos.type.*;

import java.util.List;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
  PayOsService payOsService;
  @PostMapping("/create-payment")
  public ResponseEntity<CheckoutResponseData> createPaymentLink(@RequestBody PaymentData request) throws Exception {

    CheckoutResponseData response = payOsService.createPaymentLink(request);
    return ResponseEntity.ok(response);
  }
  @GetMapping("/{orderCode}")
  public ResponseEntity<PaymentLinkData> getPaymentLinkInformation(@PathVariable("orderCode") long orderCode) throws Exception {
    PaymentLinkData paymentLinkData = payOsService.getPaymentLinkInformation(orderCode);
    return ResponseEntity.ok(paymentLinkData);
  }
  @PutMapping("/{orderCode}")
  public ResponseEntity<PaymentLinkData> cancelPaymentLink(@PathVariable("orderCode") long orderCode) throws Exception {
    PaymentLinkData paymentLinkData = payOsService.cancelPaymentLink(orderCode, "Hủy đơn hàng");
    return ResponseEntity.ok(paymentLinkData);
  }


  @PostMapping("/confirm-webhook")
  public ResponseEntity<String> confirmWebhook(@RequestBody String webhookUrl) throws Exception {
    String confirmedUrl = payOsService.confirmWebhook(webhookUrl);
    return ResponseEntity.ok(confirmedUrl);
  }

  @PostMapping("/webhook")
  public ResponseEntity<WebhookData> receiveWebhook(@RequestBody Webhook webhookBody) throws Exception {
    WebhookData verifiedData = payOsService.verifyPaymentWebhookData(webhookBody);
    log.info("Webhook received and verified: {}", verifiedData);
    return ResponseEntity.ok(verifiedData);
  }


}