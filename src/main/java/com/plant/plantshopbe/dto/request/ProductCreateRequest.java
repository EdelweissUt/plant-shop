package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.entity.Category;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {
    String name;

    String description;

    BigDecimal originalPrice = BigDecimal.ZERO;

    BigDecimal discountPrice = BigDecimal.ZERO;

    Integer stockQuantity = 0;

    Integer soldQuantity = 0;

    String size;

    Category category;


    Boolean isHidden = false;
    Boolean isDeleted = false;

}
