package com.plant.plantshopbe.configuration;

import com.plant.plantshopbe.constant.Type.CustomerTypeEnum;
import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.repository.CustomerTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    CustomerTypeRepository customerTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        createCustomerType(CustomerTypeEnum.NORMAL, new BigDecimal("0.001"), new BigDecimal("0"));
        createCustomerType(CustomerTypeEnum.VIP, new BigDecimal("0.0015"), new BigDecimal("1000000"));
        createCustomerType(CustomerTypeEnum.PREMIUM, new BigDecimal("0.002"), new BigDecimal("5000000"));
    }

    private void createCustomerType(CustomerTypeEnum type, BigDecimal multiplier, BigDecimal minSpent) {
        customerTypeRepository.findByTypeName(type).ifPresentOrElse(
                existing -> {}, // đã tồn tại thì không làm gì
                () -> {
                    CustomerType customerType = CustomerType.builder()
                            .typeName(type)
                            .pointMultiplier(multiplier)
                            .minSpent(minSpent)
                            .build();
                    customerTypeRepository.save(customerType);
                }
        );
    }
}