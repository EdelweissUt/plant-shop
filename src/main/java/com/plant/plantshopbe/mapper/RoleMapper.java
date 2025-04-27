package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.RoleRequest;
import com.plant.plantshopbe.dto.response.RoleResponse;
import com.plant.plantshopbe.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
