package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.UserCreationRequest;
import com.plant.plantshopbe.dto.request.UserUpdateRequest;
import com.plant.plantshopbe.dto.response.UserResponse;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.VerificationAndResetToken;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.UserMapper;
import com.plant.plantshopbe.repository.UserRepository;
import com.plant.plantshopbe.repository.VerificationAndResetTokenRepository;
import com.plant.plantshopbe.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    UserRepository userRepository;
    VerificationAndResetTokenRepository verificationAndResetTokenRepository;
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }
    @PostMapping("/verify-email")
    public ApiResponse<String> verifyEmail(@RequestParam String email, @RequestParam String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        VerificationAndResetToken token = verificationAndResetTokenRepository
                .findByUserAndTokenAndType(user, otp, Type.TokenType.VERIFY_EMAIL)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        user.setIsVerified(true);
        userRepository.save(user);
        verificationAndResetTokenRepository.delete(token); // hoặc để job dọn dẹp

        return ApiResponse.<String>builder()
                .result("Xác minh thành công")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser (@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request)
    {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId,request))
                .build();
    }
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username{}", authentication.getName());
        log.info("roles{}", authentication.getAuthorities());
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

}
