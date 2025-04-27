package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.BankAccount;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.BankAccountRepository;
import com.plant.plantshopbe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountService {
    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount updateBankAccount(String bankId, BankAccount bankAccount) {
        try {
            // Kiểm tra nếu tài khoản ngân hàng không tồn tại
            BankAccount existingBankAccount = bankAccountRepository.findById(bankId).orElseThrow(()-> new AppException(ErrorCode.BANK_NOT_EXITED));
            bankAccount.setId(bankId);
            bankAccountRepository.save(bankAccount);

            // Trả về tài khoản ngân hàng đã được cập nhật
            return existingBankAccount;
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            System.err.println("Error updating bank account: " + e.getMessage());
            throw new RuntimeException("Failed to update bank account.", e);
        }
    }


    public BankAccount createBankAccount(BankAccount bankAccount) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        bankAccount.setUser(user);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount getBankAccount(String bankId) {
        return bankAccountRepository.findById(bankId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public void deleteBankAccount(String bankId) {
        bankAccountRepository.deleteById(bankId);
    }
}
