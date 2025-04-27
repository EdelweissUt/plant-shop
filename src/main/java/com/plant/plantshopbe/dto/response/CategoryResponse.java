package com.plant.plantshopbe.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String id;

    String name;

    Boolean isHidden = false;
    Boolean isDeleted = false;
    List<String> urlMedias;

}
