package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.response.CategoryResponse;
import com.plant.plantshopbe.entity.Category;
import com.plant.plantshopbe.service.CategoryService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestPart("category") Category category, @RequestPart("mediaFiles")List<MultipartFile> mediaFiles){
  log.info("mé");
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(category,mediaFiles))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable("categoryId") String categoryId,@RequestPart("category") Category category,@RequestPart("mediaFiles")List<MultipartFile> mediaFiles){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId,category,mediaFiles))
                .build();
    }
    @GetMapping
    ApiResponse<List<CategoryResponse>> getCategories () {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategories())
                .build();
    }
    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> getCategory (@PathVariable("categoryId") String categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategory(categoryId))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @DeleteMapping("/{categoryId}")
    ApiResponse<String> deleteCategoty(@PathVariable("categoryId") String categoryId){
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .result("Category has been deleted")
                .build();
    }


}
