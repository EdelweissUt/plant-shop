package com.plant.plantshopbe.service;

import com.plant.plantshopbe.constant.PredefinedRole;
import com.plant.plantshopbe.constant.Type;
import com.plant.plantshopbe.dto.request.UserCreationRequest;
import com.plant.plantshopbe.dto.request.UserUpdateRequest;
import com.plant.plantshopbe.dto.response.UserResponse;
import com.plant.plantshopbe.entity.CustomerType;
import com.plant.plantshopbe.entity.Role;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.entity.VerificationAndResetToken;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.UserMapper;
import com.plant.plantshopbe.repository.CustomerTypeRepository;
import com.plant.plantshopbe.repository.RoleRepository;
import com.plant.plantshopbe.repository.UserRepository;
import com.plant.plantshopbe.repository.VerificationAndResetTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    CustomerTypeRepository customerTypeRepository;
    EmailService emailService;
    VerificationAndResetTokenRepository verificationAndResetTokenRepository;
    public User getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        // Gán loại khách hàng mặc định là NORMAL
        user.setPoints(BigDecimal.ZERO);
        CustomerType defaultType = (CustomerType) customerTypeRepository
                .findByTypeName(Type.CustomerTypeEnum.NORMAL)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        user.setCustomerType(defaultType);
        UserResponse response = userMapper.toUserResponse(userRepository.save(user));
        // Gửi email xác minh
        generateAndSendOTP(user);

        return response;
    }
    private void generateAndSendOTP(User user) {
        String otp = String.valueOf((int) ((Math.random() * 900000) + 100000)); // 6 digits
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(2);

        // Xoá token cũ cùng loại (nếu có)
        verificationAndResetTokenRepository.deleteByUserAndType(user, Type.TokenType.VERIFY_EMAIL);

        // Tạo token mới
        VerificationAndResetToken token = VerificationAndResetToken.builder()
                .token(otp)
                .type(Type.TokenType.VERIFY_EMAIL)
                .user(user)
                .expiresAt(expiresAt)
                .build();
        verificationAndResetTokenRepository.save(token);

        // Gửi email
        String subject = "Xác minh tài khoản của bạn";
        String content = buildVerificationEmail(user.getFullName(), otp);

        emailService.sendNotificationEmail(user.getEmail(), subject, content);

        log.info("OTP {} sent to {}", otp, user.getEmail());
    }


    private String buildVerificationEmail(String fullName, String otp) {
        return """
        <div style="font-family: Arial, sans-serif; max-width: 500px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;">
            <div style="text-align: center;">
                <img src="https://your-logo-url.com/logo.png" alt="Plant Shop" style="width: 80px;">
                <h2 style="color: #2d6a4f;">Chào %s,</h2>
                <p>Cảm ơn bạn đã đăng ký tài khoản tại <strong>Plant Shop</strong>.</p>
                <p>Mã xác minh của bạn là:</p>
                <h1 style="background-color: #2d6a4f; color: white; padding: 10px; border-radius: 8px;">%s</h1>
                <p>Mã này có hiệu lực trong vòng <strong>2 phút</strong>.</p>
                <p>Nếu bạn không yêu cầu tạo tài khoản, vui lòng bỏ qua email này.</p>
            </div>
        </div>
    """.formatted(fullName, otp);
    }


    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var userResponse = userMapper.toUserResponse(user);
        userResponse.setNoPassword(!StringUtils.hasText(user.getPassword()));

        return userResponse;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper ::toUserResponse).toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
