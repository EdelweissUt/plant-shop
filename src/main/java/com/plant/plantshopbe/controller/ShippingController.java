package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.response.ShippingFeeResponse;
import com.plant.plantshopbe.service.GoogleMapService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingController {

    GoogleMapService googleMapService;

    @GetMapping("/fee")
    public ResponseEntity<ShippingFeeResponse> getShippingFee(@RequestParam String address) {
        String shopAddress = "227 Nguyễn Văn Cừ, Quận 5, TP.HCM"; // hoặc load từ config

        double distance = googleMapService.getDistanceInKm( address);
        int fee = calculateShippingFee(distance); // logic tính phí ship

        return ResponseEntity.ok(new ShippingFeeResponse(distance, fee));
    }

    private int calculateShippingFee(double distanceInKm) {
        int baseFee = 15000;
        int extraFee = (int) (Math.max(0, distanceInKm - 3) * 3000);
        return baseFee + extraFee;
    }
}
