package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.response.ShippingAddressRespose;
import com.plant.plantshopbe.entity.ShippingAddress;
import com.plant.plantshopbe.service.ShippingAddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-address")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingAddressController {
    ShippingAddressService shippingAddressService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    ApiResponse<ShippingAddressRespose> createShippingAddress(@RequestBody ShippingAddress ShippingAddress){
        return ApiResponse.<ShippingAddressRespose>builder()
                .result(shippingAddressService.createShippingAddress(ShippingAddress))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{shippingAddressId}")
    ApiResponse<ShippingAddressRespose> updateShippingAddress(@PathVariable("shippingAddressId") String shippingAddressId,@RequestBody ShippingAddress ShippingAddress){
        return ApiResponse.<ShippingAddressRespose>builder()
                .result(shippingAddressService.updateShippingAddress(shippingAddressId,ShippingAddress))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    ApiResponse<List<ShippingAddressRespose>> getShippingAddresses () {
        return ApiResponse.<List<ShippingAddressRespose>>builder()
                .result(shippingAddressService.getShippingAddresses())
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{shippingAddressId}")
    ApiResponse<ShippingAddressRespose> getShippingAddress (@PathVariable("shippingAddressId") String shippingAddressId){
        return ApiResponse.<ShippingAddressRespose>builder()
                .result(shippingAddressService.getShippingAddress(shippingAddressId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{shippingAddressId}")
    ApiResponse<String> deleteCategoty(@PathVariable("shippingAddressId") String shippingAddressId){
        shippingAddressService.deleteShippingAddress(shippingAddressId);
        return ApiResponse.<String>builder()
                .result("ShippingAddress has been deleted")
                .build();
    }

}
