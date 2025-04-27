package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.UserCreationRequest;
import com.plant.plantshopbe.dto.request.UserUpdateRequest;
import com.plant.plantshopbe.dto.response.UserResponse;
import com.plant.plantshopbe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
