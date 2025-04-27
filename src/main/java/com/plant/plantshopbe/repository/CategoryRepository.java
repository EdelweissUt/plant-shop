package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.Category;
import com.plant.plantshopbe.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
}
