package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    String fullName;

    String phone;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

    Boolean isVerified;

    Boolean isBlocked;

    Boolean isDeleted;

    List<String> roles;

    CustomerType customerType;

    BigDecimal points;

}
