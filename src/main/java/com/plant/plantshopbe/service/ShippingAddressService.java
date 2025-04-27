package com.plant.plantshopbe.service;

import com.plant.plantshopbe.dto.response.ShippingAddressRespose;
import com.plant.plantshopbe.entity.ShippingAddress;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.ShippingAddressMapper;
import com.plant.plantshopbe.repository.ShippingAddressRepository;
import com.plant.plantshopbe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingAddressService {
    ShippingAddressRepository shippingAddressRepository;
    UserRepository userRepository;
    ShippingAddressMapper shippingAddressMapper;

    public List<ShippingAddressRespose> getShippingAddresses() {
        return shippingAddressRepository.findAll().stream().map(shippingAddressMapper::toShippingAddressResponse).toList();
    }

    public ShippingAddressRespose updateShippingAddress(String shippingAddressId, ShippingAddress shippingAddress) {
        try {
            ShippingAddress existingShippingAddress = shippingAddressRepository.findById(shippingAddressId).orElseThrow(() -> new AppException(ErrorCode.BANK_NOT_EXITED));
            shippingAddress.setId(shippingAddressId);

            return shippingAddressMapper.toShippingAddressResponse(shippingAddressRepository.save(shippingAddress));
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            System.err.println("Error updating bank account: " + e.getMessage());
            throw new RuntimeException("Failed to update bank account.", e);
        }
    }


    public ShippingAddressRespose createShippingAddress(ShippingAddress shippingAddress) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shippingAddress.setUser(user);
        return shippingAddressMapper.toShippingAddressResponse(shippingAddressRepository.save(shippingAddress));
    }

    public ShippingAddressRespose getShippingAddress(String shippingAddressId) {
        return shippingAddressMapper.toShippingAddressResponse(shippingAddressRepository.findById(shippingAddressId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public void deleteShippingAddress(String shippingAddressId) {
        shippingAddressRepository.deleteById(shippingAddressId);
    }
}
