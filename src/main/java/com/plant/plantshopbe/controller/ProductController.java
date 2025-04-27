package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.ProductCreateRequest;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {
    ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "sizeFilter", required = false) String sizeFilter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<ProductResponse> result = productService.getProducts(keyword, categoryId, minPrice, maxPrice, sizeFilter, page, size);
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(result)
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestPart("product") ProductCreateRequest request, @RequestPart("mediaFiles") List<MultipartFile> mediaFiles){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request,mediaFiles))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable("productId") String productId, @RequestPart("Product") Product product, @RequestPart("mediaFiles")List<MultipartFile> mediaFiles){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId,product,mediaFiles))
                .build();
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProduct (@PathVariable("productId") String productId){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(productId))
                .build();
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @DeleteMapping("/{productId}")
    ApiResponse<String> deleteCategoty(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .result("Product has been deleted")
                .build();
    }
    @GetMapping("/top-selling")
    public ApiResponse<Page<ProductResponse>> getTopSellingProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.getTopSellingProducts(page, size))
                .build();
    }
    
}
