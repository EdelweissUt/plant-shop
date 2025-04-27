package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.OrderRequest;
import com.plant.plantshopbe.dto.response.OrderResponse;
import com.plant.plantshopbe.entity.OrderItem;
import com.plant.plantshopbe.entity.Order;
import com.plant.plantshopbe.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ApiResponse<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/all")
    public ApiResponse<Page<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Type.OrderStatus status,
            @RequestParam(required = false) Type.PaymentStatus paymentStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String keyword) {

        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.getOrders(page, size, status, paymentStatus, startDate, endDate, keyword))
                .build();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ApiResponse<Page<OrderResponse>> getMyOrders(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) Type.OrderStatus status,
                                                        @RequestParam(required = false) Type.PaymentStatus paymentStatus,
                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.getMyOrders(page, size, status, paymentStatus, fromDate, toDate))
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF','ROLE_USER')")
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable("orderId") String orderId) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrder(orderId))
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PutMapping("/{orderId}/status")
    public ApiResponse<String> updateOrderStatus(@PathVariable String orderId,
                                                 @RequestParam Type.OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return ApiResponse.<String>builder()
                .result("Order status updated successfully")
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/{orderId}/cancel")
    public ApiResponse<String> cancelOrder(@PathVariable String orderId, @RequestParam String reasonCancel) {
        orderService.cancelOrder(orderId, reasonCancel);
        return ApiResponse.<String>builder()
                .result("Order status updated successfully")
                .build();
    }

    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    @PutMapping("/{orderId}/delivered")
    public ApiResponse<String> markAsDelivered(@PathVariable String orderId) {
        orderService.markAsDelivered(orderId);
        return ApiResponse.<String>builder().result("Order marked as delivered").build();
    }

    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    @PutMapping("/{orderId}/shipped")
    public ApiResponse<String> markAsShipped(@PathVariable String orderId) {
        orderService.markAsShipped(orderId);
        return ApiResponse.<String>builder().result("Order marked as delivered").build();
    }

    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    @PutMapping("/{orderId}/completed")
    public ApiResponse<String> markAsCompleted(@PathVariable String orderId) {
        orderService.markAsCompleted(orderId);
        return ApiResponse.<String>builder().result("Order marked as delivered").build();
    }
    @GetMapping("/recent")
    public ApiResponse<OrderResponse> getLatestOrder() {
        return ApiResponse.<OrderResponse>builder().result(orderService.getLatestOrder()).build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/statistics")
    public ApiResponse<?> getStatistics(@RequestParam(required = false) LocalDate fromDate,
                                        @RequestParam(required = false) LocalDate toDate) {
        return ApiResponse.builder().result(orderService.getOrderStatistics(fromDate, toDate)).build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/{orderId}/history")
    public ApiResponse<?> getOrderHistory(@PathVariable String orderId) {
        return ApiResponse.builder().result(orderService.getOrderHistory(orderId)).build();
    }


}
