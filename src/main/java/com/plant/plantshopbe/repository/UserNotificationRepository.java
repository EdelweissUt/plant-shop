package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
    List<UserNotification> findByUser(User user);
    List<UserNotification> findByUserAndIsReadFalse(User user);

}