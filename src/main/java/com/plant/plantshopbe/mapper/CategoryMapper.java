package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.response.CategoryResponse;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.entity.Category;
import com.plant.plantshopbe.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
}
