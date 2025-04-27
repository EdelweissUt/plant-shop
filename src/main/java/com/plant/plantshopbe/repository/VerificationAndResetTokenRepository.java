package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.VerificationAndResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationAndResetTokenRepository extends JpaRepository<VerificationAndResetToken, String> {
    Optional<VerificationAndResetToken> findByUserAndTokenAndType(User user, String token, Type.TokenType type);

    void deleteByUserAndType(User user, Type.TokenType type);
}