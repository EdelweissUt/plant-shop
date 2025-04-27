package com.plant.plantshopbe.controller;
import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.entity.Banner;
import com.plant.plantshopbe.service.BannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BannerController {
    BannerService bannerService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PostMapping
    ApiResponse<Banner> createBanner(@RequestBody Banner banner){
        return ApiResponse.<Banner>builder()
                .result(bannerService.createBanner(banner))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PutMapping("/{bannerId}")
    ApiResponse<Banner> updateBanner(@PathVariable("bannerId") String bannerId,@RequestBody Banner banner){
        return ApiResponse.<Banner>builder()
                .result(bannerService.updateBanner(bannerId,banner))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping
    ApiResponse<List<Banner>> getBanners () {
        return ApiResponse.<List<Banner>>builder()
                .result(bannerService.getBanners())
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping("/{bannerId}")
    ApiResponse<Banner> getBanner (@PathVariable("bannerId") String bannerId){
        return ApiResponse.<Banner>builder()
                .result(bannerService.getBanner(bannerId))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @DeleteMapping("/{bannerId}")
    ApiResponse<String> deleteBanner(@PathVariable("bannerId") String bannerId){
        bannerService.deleteBanner(bannerId);
        return ApiResponse.<String>builder()
                .result("Banner has been deleted")
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @GetMapping("/active")
    public ApiResponse<List<Banner>> getActiveBanners() {
        return ApiResponse.<List<Banner>>builder()
                .result(bannerService.getActiveBanners())
                .build();
    }

}
