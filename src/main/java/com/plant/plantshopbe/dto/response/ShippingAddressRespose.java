package com.plant.plantshopbe.dto.response;

import com.plant.plantshopbe.entity.Order;
import com.plant.plantshopbe.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingAddressRespose {

    String id;
    User user;

    String recipientName;

    String phone;

    String address; // Địa chỉ chi tiết: số nhà, tên đường

    String ward; // Phường/Xã

    String district; // Quận/Huyện

    String province; // Tỉnh/Thành phố
}
