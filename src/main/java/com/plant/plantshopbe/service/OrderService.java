package com.plant.plantshopbe.service;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.NotificationRequest;
import com.plant.plantshopbe.dto.request.OrderItemRequest;
import com.plant.plantshopbe.dto.request.OrderRequest;
import com.plant.plantshopbe.dto.response.OrderResponse;
import com.plant.plantshopbe.entity.*;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.OrderMapper;
import com.plant.plantshopbe.repository.*;
import com.plant.plantshopbe.util.Helper;
import com.plant.plantshopbe.util.PDFInvoiceGenerator;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    ShippingAddressRepository shippingAddressRepository;
    DiscountCodeRepository discountCodeRepository;
    DiscountUsageRepository discountUsageRepository;
    NotificationService notificationService;
    EmailService emailService;
    OrderMapper orderMapper;
    OrderHistoryRepository orderHistoryRepository;
    GoogleMapService googleMapService;
    InvoiceRepository invoiceRepository;
    Helper helper;

    public User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = getCurrentUser();


        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // Lấy dữ liệu sản phẩm và tạo danh sách OrderItem
        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            BigDecimal itemTotal = product.getDiscountPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getDiscountPrice())
                    .build();
            orderItems.add(orderItem);
        }

        // Xử lý mã giảm giá nếu có
        DiscountCode discountCode = null;
        BigDecimal finalAmount = totalPrice;

        if (request.getDiscountCode() != null && !request.getDiscountCode().isBlank()) {
            discountCode = discountCodeRepository.findByCode(request.getDiscountCode())
                    .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));

            if (!discountCode.isValidForUse(totalPrice, user)) {
                throw new AppException(ErrorCode.DISCOUNT_INVALID);
            }

            finalAmount = discountCode.applyDiscount(totalPrice);

            // Giảm số lượng mã
            discountCode.setQuantity(discountCode.getQuantity() - 1);
            discountCodeRepository.save(discountCode);
        }
        /// xử lý tích điểm nếu có
        BigDecimal usedPoints = request.getUsedPoints() != null ? request.getUsedPoints() : BigDecimal.ZERO;

        if (usedPoints.compareTo(BigDecimal.ZERO) > 0) {
            if (user.getPoints().compareTo(usedPoints) < 0) {
                throw new AppException(ErrorCode.NOT_ENOUGH_POINTS);
            }

            // Trừ điểm dùng vào finalAmount
            finalAmount = finalAmount.subtract(usedPoints);

            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            // Trừ điểm của người dùng
            user.setPoints(user.getPoints().subtract(usedPoints));
            userRepository.save(user);
        }

        // Xử lý phí ship
        /*ShippingAddress shippingAddress = request.getShippingAddress();
        BigDecimal shippingFee = calculateShippingFee(shippingAddress.getProvince());
        finalAmount = finalAmount.add(shippingFee);*/
        BigDecimal shipping_fee = new BigDecimal(3000);
        // Tạo đơn hàng
        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .finalAmount(finalAmount)
                .shippingFee(shipping_fee)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(Type.PaymentStatus.PENDING)
                .status(Type.OrderStatus.PENDING)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .shippingAddress(request.getShippingAddress())
                .build();

        Order savedOrder = orderRepository.save(order);

        // Lưu OrderItems & cập nhật tồn kho
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }
        orderItemRepository.saveAll(orderItems);
        //

        if (discountCode != null) {
            DiscountUsage discountUsage = DiscountUsage.builder()
                    .discountCode(discountCode)
                    .usedAt(LocalDateTime.now())
                    .order(savedOrder)
                    .user(user)
                    .build();
            discountUsageRepository.save(discountUsage);
        }

        // ✅ Lưu lịch sử đơn hàng
        OrderHistory orderHistory = OrderHistory.builder()
                .order(savedOrder)
                .status(savedOrder.getStatus())
                .changedAt(LocalDateTime.now())
                .changedBy(user.getEmail())
                .note("Đơn hàng được tạo mới")
                .build();
        orderHistoryRepository.save(orderHistory);

        // Gửi email xác nhận (nếu cần)
        emailService.sendOrderConfirmationEmail(user.getEmail(), savedOrder);

        // Gửi thông báo
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("Đặt hàng thành công")
                .message("Đơn hàng của bạn đã được tạo thành công với mã: " + savedOrder.getId())
                .type(Type.NotificationType.ORDER_UPDATE)
                .build();
        Notification notification = notificationService.createNotification(notificationRequest);
        notificationService.sendNotificationToUsers(notification.getId(), List.of(user.getId()));
        savedOrder.setItems(orderItems);
        savedOrder.setOrderCode(generateOrderCode());
        OrderResponse response = orderMapper.toOrderResponse(savedOrder);

        return response;
    }
    private BigDecimal calculateShippingFee(ShippingAddress address) {
        String fullAddress = String.join(", ",
                address.getAddress(),
                address.getWard(),
                address.getDistrict(),
                address.getProvince());

        double distance = googleMapService.getDistanceInKm(fullAddress);
        BigDecimal baseFee = new BigDecimal(10000); // 10k cơ bản
        BigDecimal perKm = new BigDecimal(2000); // 2k/km

        return baseFee.add(perKm.multiply(BigDecimal.valueOf(distance)));
    }
    private long generateOrderCode() {
        long code;
        do {
            code = 100000 + new Random().nextInt(900000);
        } while (orderRepository.existsByOrderCode(code));
        return code;
    }

    @Transactional
    public void updateOrderStatus(String orderId, Type.OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (newStatus == Type.OrderStatus.CANCELLED && order.getStatus() != Type.OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        Type.OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);

        // ✅ TÍCH ĐIỂM nếu trạng thái chuyển sang COMPLETED
        if (newStatus == Type.OrderStatus.COMPLETED) {
            BigDecimal totalSpent = order.getTotalPrice(); // hoặc tổng từ các orderItem
            User user = savedOrder.getUser();
            CustomerType customerType = user.getCustomerType();
            BigDecimal multiplier = (customerType != null && customerType.getPointMultiplier() != null)
                    ? customerType.getPointMultiplier()
                    : BigDecimal.ONE; // Mặc định nếu không có

            BigDecimal earnedPoints = totalSpent.multiply(multiplier);
            user.setPoints(user.getPoints().add(earnedPoints));
            userRepository.save(user);
            createInvoiceFromOrder(order);

        }

        // Gửi email xác nhận
        String message = "Trạng thái đơn hàng " + orderId + " được cập nhật " + newStatus;
        emailService.sendOrderStatusEmail(savedOrder.getUser().getEmail(), message);

        // Gửi thông báo
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("Trạng thái đơn hàng " + savedOrder.getId() + " được cập nhật")
                .message("Từ " + oldStatus + " → " + newStatus)
                .type(Type.NotificationType.ORDER_UPDATE)
                .build();
        Notification notification = notificationService.createNotification(notificationRequest);
        notificationService.sendNotificationToUsers(notification.getId(), List.of(savedOrder.getUser().getId()));

        // Lưu lịch sử trạng thái
        OrderHistory history = OrderHistory.builder()
                .order(order)
                .status(newStatus)
                .changedAt(LocalDateTime.now())
                .changedBy(getCurrentUser().getEmail()) // hoặc admin ID
                .note("Cập nhật từ " + oldStatus + " → " + newStatus)
                .build();
        orderHistoryRepository.save(history);
    }

    public Invoice createInvoiceFromOrder(Order order) {
        LocalDateTime now = LocalDateTime.now();

        long countToday = invoiceRepository.countByIssuedDateBetween(
                now.toLocalDate().atStartOfDay(),
                now.toLocalDate().plusDays(1).atStartOfDay()
        );

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setIssuedDate(now);

        invoice.setTaxCode(helper.generateAutoCode("TAX", now, countToday));
        invoice.setInvoiceNumber(helper.generateAutoCode("INV", now, countToday));

        invoice.setCompanyName("Công ty TNHH Plant");
        invoice.setCompanyAddress("123 Cách Mạng Tháng 8, Q1, TP.HCM");

        return invoiceRepository.save(invoice);
    }

    public Page<OrderResponse> getOrders(int page, int size, Type.OrderStatus status,
                                         Type.PaymentStatus paymentStatus,
                                         LocalDateTime startDate, LocalDateTime endDate,
                                         String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Order> spec = Specification.where((root, query, cb) -> cb.isFalse(root.get("isDeleted")));

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (paymentStatus != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentStatus"), paymentStatus));
        }
        if (startDate != null && endDate != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("createdAt"), startDate, endDate));
        }
        if (keyword != null && !keyword.isBlank()) {
            String likeKeyword = "%" + keyword.trim().toLowerCase() + "%";

            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> userJoin = root.join("user", JoinType.LEFT);
                Join<Object, Object> customerTypeJoin = userJoin.join("customerType", JoinType.LEFT);

                return cb.or(
                        cb.like(cb.lower(userJoin.get("email")), likeKeyword),
                        cb.like(cb.lower(userJoin.get("phone")), likeKeyword),
                        cb.like(cb.lower(customerTypeJoin.get("typeName")), likeKeyword)
                );
            });
        }

        Page<Order> orders = orderRepository.findAll(spec, pageable);
        return orders.map(orderMapper::toOrderResponse);

    }

    public Page<OrderResponse> getMyOrders(int page, int size, Type.OrderStatus status,
                                           Type.PaymentStatus paymentStatus,
                                           LocalDateTime startDate, LocalDateTime endDate) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return orderRepository.searchMyOrders(user.getId(), status, paymentStatus, startDate, endDate, pageable)
                .map(orderMapper::toOrderResponse);
    }

    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }


    @Transactional
    public void cancelOrder(String orderId, String reasonCancel) {
        User user = getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Chỉ cho hủy nếu đơn đang chờ xử lý
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (order.getStatus() != Type.OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        //  Cập nhật trạng thái
        order.setStatus(Type.OrderStatus.CANCELLED);
        orderRepository.save(order);

        //  Hoàn lại điểm đã dùng (nếu có)
        if (order.getUsedPoints() != null && order.getUsedPoints().compareTo(BigDecimal.ZERO) > 0) {
            user.setPoints(user.getPoints().add(order.getUsedPoints()));
            userRepository.save(user);
        }

        //  Hoàn lại mã giảm giá đã dùng (nếu có)
        DiscountUsage usage = discountUsageRepository.findByOrderId(orderId).orElse(null);
        if (usage != null) {
            DiscountCode discountCode = usage.getDiscountCode();
            discountCode.setQuantity(discountCode.getQuantity() + 1);
            discountCodeRepository.save(discountCode);
            discountUsageRepository.delete(usage); // hoặc giữ lại nếu muốn thống kê
        }

        //  Ghi lại lịch sử trạng thái
        OrderHistory history = OrderHistory.builder()
                .order(order)
                .status(Type.OrderStatus.CANCELLED)
                .reasonCancel(reasonCancel)
                .changedAt(LocalDateTime.now())
                .changedBy(user.getEmail())
                .note("Người dùng hủy đơn hàng")
                .build();
        orderHistoryRepository.save(history);

        //  Gửi email và thông báo (nếu bạn muốn)
        emailService.sendOrderStatusEmail(user.getEmail(), "Đơn hàng " + orderId + " đã bị hủy.");
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("Đơn hàng đã bị hủy")
                .message("Đơn hàng " + orderId + " của bạn đã được hủy thành công.")
                .type(Type.NotificationType.ORDER_UPDATE)
                .build();
        Notification notification = notificationService.createNotification(notificationRequest);
        notificationService.sendNotificationToUsers(notification.getId(), List.of(user.getId()));
    }



    public void markAsDelivered(String orderId) {
       updateOrderStatus(orderId, Type.OrderStatus.DELIVERED);
    }

    public OrderResponse getLatestOrder() {
        return orderRepository.findTopByOrderByCreatedAtDesc()
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    public Map<String, Object> getOrderStatistics(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate != null ? fromDate.atStartOfDay() : LocalDate.now().minusMonths(1).atStartOfDay();
        LocalDateTime end = toDate != null ? toDate.atTime(23, 59, 59) : LocalDate.now().atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByCreatedAtBetween(start, end);

        BigDecimal totalRevenue = orders.stream()
                .filter(o -> o.getStatus() == Type.OrderStatus.DELIVERED)
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orders.size();
        long deliveredCount = orders.stream().filter(o -> o.getStatus() == Type.OrderStatus.DELIVERED).count();
        long cancelledCount = orders.stream().filter(o -> o.getStatus() == Type.OrderStatus.CANCELLED).count();

        return Map.of(
                "totalOrders", totalOrders,
                "deliveredOrders", deliveredCount,
                "cancelledOrders", cancelledCount,
                "revenue", totalRevenue
        );
    }

    public List<Map<String, Object>> getOrderHistory(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        List<OrderHistory> historyList = orderHistoryRepository.findByOrderIdOrderByChangedAtDesc(orderId);

        return historyList.stream().map(history -> {
            Map<String, Object> map = new HashMap<>();
            map.put("status", history.getStatus());
            map.put("reasonCancel", history.getReasonCancel());
            map.put("changedAt", history.getChangedAt());
            map.put("changedBy", history.getChangedBy());
            map.put("note", history.getNote());
            return map;
        }).collect(Collectors.toList());
    }


    public void markAsCompleted(String orderId) {
        updateOrderStatus(orderId, Type.OrderStatus.COMPLETED);

    }

    public void markAsShipped(String orderId) {
        updateOrderStatus(orderId, Type.OrderStatus.SHIPPED);

    }
}
