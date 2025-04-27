package com.plant.plantshopbe.dto.response;

import com.plant.plantshopbe.entity.CustomerType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;
    String fullName;
    LocalDate dob;
    Boolean noPassword;
    Set<RoleResponse> roles;
    CustomerType customerType;
}