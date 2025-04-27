package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;


    String fullName;
    String phone;
    Boolean isVerified = false;
    Boolean isBlocked = false;
    Boolean isDeleted = false;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

}
