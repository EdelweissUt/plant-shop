package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.entity.Order;
import com.plant.plantshopbe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    // ================================
    // Các phương thức đơn giản
    // ================================

    Page<Order> findAllByUser(User user, Pageable pageable);

    Page<Order> findByUserId(String userId, Pageable pageable);

    Page<Order> findByStatus(Type.OrderStatus status, Pageable pageable);

    Page<Order> findByUserIdAndStatus(String userId, Type.OrderStatus status, Pageable pageable);

    Page<Order> findByUserIdAndIsDeletedFalse(String userId, Pageable pageable);

    Page<Order> findAllByIsDeletedFalse(Pageable pageable);
    Optional<Order> findByIdAndAndUser(String id, User user);

    Optional<Order> findByIdAndIsDeletedFalse(String id);

    Optional<Order> findTopByOrderByCreatedAtDesc();
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findAllByCreatedAtBetweenAndIsDeletedFalse(LocalDateTime from, LocalDateTime to);

    // ================================
    // Các phương thức nâng cao dùng filter
    // ================================

    @Query("SELECT o FROM Order o " +
            "WHERE o.isDeleted = false " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus) " +
            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate) " +
            "AND (" +
            "   :keyword IS NULL OR :keyword = '' OR " +
            "   LOWER(CAST(o.id AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(o.user.customerType.typeName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(o.user.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "   LOWER(o.user.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    Page<Order> searchOrders(@Param("status") Type.OrderStatus status,
                             @Param("paymentStatus") Type.PaymentStatus paymentStatus,
                             @Param("fromDate") LocalDateTime fromDate,
                             @Param("toDate") LocalDateTime toDate,
                             @Param("keyword") String keyword,
                             Pageable pageable);


    @Query("SELECT o FROM Order o " +
            "WHERE o.user.id = :userId " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus) " +
            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate)")
    Page<Order> searchMyOrders(@Param("userId") String userId,
                               @Param("status") Type.OrderStatus status,
                               @Param("paymentStatus") Type.PaymentStatus paymentStatus,
                               @Param("fromDate") LocalDateTime fromDate,
                               @Param("toDate") LocalDateTime toDate,
                               Pageable pageable);

    // ================================
    // Các filter theo khoảng thời gian
    // ================================

    Page<Order> findByUserIdAndIsDeletedFalseAndStatusAndPaymentStatusAndCreatedAtBetween(
            String userId,
            Type.OrderStatus status,
            Type.PaymentStatus paymentStatus,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );

    Page<Order> findByIsDeletedFalseAndStatusAndPaymentStatusAndCreatedAtBetween(
            Type.OrderStatus status,
            Type.PaymentStatus paymentStatus,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable
    );

    boolean existsByOrderCode(long code);
}
