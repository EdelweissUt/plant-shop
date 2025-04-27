package com.plant.plantshopbe.dto.request;

import com.plant.plantshopbe.constant.Type;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerTypeRequest {
    Type.CustomerTypeEnum typeName = Type.CustomerTypeEnum.NORMAL ;

    BigDecimal pointMultiplier =BigDecimal.ZERO;;

    BigDecimal minSpent= BigDecimal.ZERO;
}
