package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.ProductCreateRequest;
import com.plant.plantshopbe.dto.request.ProductUpdateRequest;
import com.plant.plantshopbe.dto.request.UserUpdateRequest;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.dto.response.UserResponse;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")

public interface ProductMapper {

        Product toProduct(ProductCreateRequest request);

        ProductResponse toProductResponse(Product product);

        @Mapping(target = "category", ignore = true)
        void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);


}
