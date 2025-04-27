package com.plant.plantshopbe.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class Helper {
    public String generateAutoCode(String prefix, LocalDateTime issuedDate, long countOfDay) {
        String datePart = issuedDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return prefix + "-" + datePart + "-" + String.format("%04d", countOfDay + 1);
    }

}
