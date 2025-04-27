package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media,String> {
    Optional<Media>findByEntityTypeAndEntityId(String entityType, String entityId );
    Optional<List<Media>>findAllByEntityTypeAndEntityId(String entityType, String entityId );

}
