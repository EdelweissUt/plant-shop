package com.plant.plantshopbe.service;
import com.plant.plantshopbe.entity.Banner;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.BannerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BannerService {
    BannerRepository bannerRepository;

    public List<Banner> getBanners() {
        return bannerRepository.findAll();
    }

    public Banner updateBanner(String bannerId, Banner Banner) {
        try {
            // Kiểm tra nếu tài khoản ngân hàng không tồn tại
            Banner existingBanner = bannerRepository.findById(bannerId).orElseThrow(()-> new AppException(ErrorCode.BANK_NOT_EXITED));
            Banner.setId(bannerId);
            bannerRepository.save(Banner);

            // Trả về tài khoản ngân hàng đã được cập nhật
            return existingBanner;
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            System.err.println("Error updating bank account: " + e.getMessage());
            throw new RuntimeException("Failed to update bank account.", e);
        }
    }


    public Banner createBanner(Banner Banner) {
        return bannerRepository.save(Banner);
    }

    public Banner getBanner(String bannerId) {
        return bannerRepository.findById(bannerId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public void deleteBanner(String bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    public List<Banner> getActiveBanners() {
        return bannerRepository.findByIsActiveTrue();
    }

}
