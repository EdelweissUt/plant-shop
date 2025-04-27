package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, String> {
    Optional<Object> findByTypeName(Type.CustomerTypeEnum type);
}