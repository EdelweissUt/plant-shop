package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.service.CustomerTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerTypeController {

    CustomerTypeService customerTypeService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ApiResponse<CustomerType> createCustomerType(@RequestBody CustomerType customerType) {
        return ApiResponse.<CustomerType>builder()
                .result(customerTypeService.createCustomerType(customerType))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ApiResponse<CustomerType> updateCustomerType(@PathVariable String id, @RequestBody CustomerType customerType) {
        return ApiResponse.<CustomerType>builder()
                .result(customerTypeService.updateCustomerType(id, customerType))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    ApiResponse<List<CustomerType>> getAllCustomerTypes() {
        return ApiResponse.<List<CustomerType>>builder()
                .result(customerTypeService.getAllCustomerTypes())
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping("/{id}")
    ApiResponse<CustomerType> getCustomerType(@PathVariable String id) {
        return ApiResponse.<CustomerType>builder()
                .result(customerTypeService.getCustomerType(id))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteCustomerType(@PathVariable String id) {
        customerTypeService.deleteCustomerType(id);
        return ApiResponse.<String>builder()
                .result("CustomerType has been deleted")
                .build();
    }
}