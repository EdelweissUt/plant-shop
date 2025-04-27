package com.plant.plantshopbe.mapper;

import com.plant.plantshopbe.dto.request.PermissionRequest;
import com.plant.plantshopbe.dto.response.PermissionResponse;
import com.plant.plantshopbe.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
