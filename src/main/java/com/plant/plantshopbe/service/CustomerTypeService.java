package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.Wishlist;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.CustomerTypeRepository;
import com.plant.plantshopbe.repository.ProductRepository;
import com.plant.plantshopbe.repository.WishlistRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerTypeService {
    CustomerTypeRepository customerTypeRepository;

    public CustomerType createCustomerType(CustomerType customerType) {
        return customerTypeRepository.save(customerType);
    }

    public CustomerType updateCustomerType(String id, CustomerType customerType) {
        CustomerType existing = customerTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        existing.setTypeName(customerType.getTypeName());
        existing.setPointMultiplier(customerType.getPointMultiplier());
        existing.setMinSpent(customerType.getMinSpent());
        return customerTypeRepository.save(existing);
    }

    public List<CustomerType> getAllCustomerTypes() {
        return customerTypeRepository.findAll();
    }

    public CustomerType getCustomerType(String id) {
        return customerTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    public void deleteCustomerType(String id) {
        CustomerType existing = customerTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        customerTypeRepository.delete(existing);
    }
}