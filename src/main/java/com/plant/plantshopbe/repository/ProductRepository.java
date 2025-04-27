package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.dto.request.ProductCreateRequest;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.ContentHandler;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findAllByOrderBySoldQuantityDesc(Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:minPrice IS NULL OR p.discountPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.discountPrice <= :maxPrice) " +
            "AND (:sizeFilter IS NULL OR p.size = :sizeFilter)")
    Page<Product> searchProductsWithFilters(
            @Param("keyword") String keyword,
            @Param("categoryId") String categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("sizeFilter") String sizeFilter,
            Pageable pageable
    );


    Page<Product> findAllByIsHiddenFalseAndIsDeletedFalseOrderBySoldQuantityDesc(Pageable top100Pageable);
}
