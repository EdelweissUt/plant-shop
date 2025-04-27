package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.ProductCreateRequest;
import com.plant.plantshopbe.dto.request.ProductUpdateRequest;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.dto.response.ShippingAddressRespose;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface ShippingAddressMapper {


        ShippingAddressRespose toShippingAddressResponse(ShippingAddress shippingAddress);




}
