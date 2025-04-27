package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.entity.BankAccount;
import com.plant.plantshopbe.entity.DiscountCode;
import com.plant.plantshopbe.service.DiscountCodeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount-code")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DiscountCodeController {
    DiscountCodeService discountCodeService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ApiResponse<DiscountCode> createDiscountCode(@RequestBody DiscountCode discountCode){
        return ApiResponse.<DiscountCode>builder()
                .result(discountCodeService.createDiscountCode(discountCode))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{discountCodeId}")
    ApiResponse<DiscountCode> updateDiscountCode(@PathVariable("discountCodeId") String discountCodeId, @RequestBody DiscountCode discountCode){
        return ApiResponse.<DiscountCode>builder()
                .result(discountCodeService.updateDiscountCode(discountCodeId,discountCode))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping
    ApiResponse<List<DiscountCode>> getDiscountCodes () {
        return ApiResponse.<List<DiscountCode>>builder()
                .result(discountCodeService.getDiscountCodes())
                .build();
    }

    @GetMapping("/{discountCodeId}")
    ApiResponse<DiscountCode> getDiscountCode (@PathVariable("discountCodeId") String discountCodeId){
        return ApiResponse.<DiscountCode>builder()
                .result(discountCodeService.getDiscountCode(discountCodeId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{discountCodeId}")
    ApiResponse<String> deleteDiscountCode(@PathVariable("discountCodeId") String discountCodeId){
        discountCodeService.deleteDiscountCode(discountCodeId);
        return ApiResponse.<String>builder()
                .result("DiscountCode has been deleted")
                .build();
    }
}
