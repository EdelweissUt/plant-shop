package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.entity.BankAccount;
import com.plant.plantshopbe.service.BankAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BankAccountController {
    BankAccountService bankAccountService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    ApiResponse<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){
        return ApiResponse.<BankAccount>builder()
                .result(bankAccountService.createBankAccount(bankAccount))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{bankId}")
    ApiResponse<BankAccount> updateBankAccount(@PathVariable("bankId") String bankId,@RequestBody BankAccount bankAccount){
        return ApiResponse.<BankAccount>builder()
                .result(bankAccountService.updateBankAccount(bankId,bankAccount))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    ApiResponse<List<BankAccount>> getBankAccounts () {
        return ApiResponse.<List<BankAccount>>builder()
                .result(bankAccountService.getBankAccounts())
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{bankId}")
    ApiResponse<BankAccount> getBankAccount (@PathVariable("bankId") String bankId){
        return ApiResponse.<BankAccount>builder()
                .result(bankAccountService.getBankAccount(bankId))
                .build();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{bankId}")
    ApiResponse<String> deleteBankAccount(@PathVariable("bankId") String bankId){
        bankAccountService.deleteBankAccount(bankId);
        return ApiResponse.<String>builder()
                .result("BankAccount has been deleted")
                .build();
    }

}
