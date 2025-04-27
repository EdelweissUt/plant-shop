package com.plant.plantshopbe.repository;

import com.plant.plantshopbe.entity.BankAccount;
import com.plant.plantshopbe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
