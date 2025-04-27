package com.plant.plantshopbe.service;

import com.plant.plantshopbe.dto.request.ApiResponse;
import com.plant.plantshopbe.dto.request.ProductCreateRequest;
import com.plant.plantshopbe.dto.request.UserCreationRequest;
import com.plant.plantshopbe.dto.response.CategoryResponse;
import com.plant.plantshopbe.dto.response.CloudinaryResponse;
import com.plant.plantshopbe.dto.response.ProductResponse;
import com.plant.plantshopbe.dto.response.UserResponse;
import com.plant.plantshopbe.entity.Category;
import com.plant.plantshopbe.entity.Media;
import com.plant.plantshopbe.entity.Product;
import com.plant.plantshopbe.entity.User;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.ProductMapper;
import com.plant.plantshopbe.repository.MediaRepository;
import com.plant.plantshopbe.repository.ProductRepository;
import com.plant.plantshopbe.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    CloudinaryService cloudinaryService;
    String entityType ="Product";
    MediaService mediaService;

    public Page<ProductResponse> getProducts(String keyword,
                                             String categoryId,
                                             BigDecimal minPrice,
                                             BigDecimal maxPrice,
                                             String sizeFilter,
                                             int page,
                                             int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.searchProductsWithFilters(
                keyword, categoryId, minPrice, maxPrice, sizeFilter, pageable
        );

        Page<ProductResponse> responses = products.map(productMapper::toProductResponse);
        responses.forEach(response -> {
            List<String> urls = mediaService
                    .getMedias(entityType, response.getId())
                    .stream()
                    .map(Media::getMediaUrl)
                    .toList();
            response.setUrlMedias(urls);
        });
        return responses;
    }


    public ProductResponse createProduct(ProductCreateRequest request, List<MultipartFile> mediaFiles) {
        Product product = productMapper.toProduct(request);
        Product savedProduct = productRepository.save(product);
        // Xử lý media files
        uploadFiles(entityType,savedProduct,mediaFiles);
        List<String> urlMedias = mediaService.getMedias(entityType,savedProduct.getId()).stream().map(Media::getMediaUrl).toList();
       ProductResponse response = productMapper.toProductResponse(savedProduct);
       response.setUrlMedias(urlMedias);
        return response ;

    }
    public void uploadFiles(String entityType, Product product, List<MultipartFile> mediaFiles){
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            for (MultipartFile file : mediaFiles) {
                // Upload file lên Cloudinary
                String fileName = FileUploadUtil.getFileName(product.getName());
                var cloudinaryResponse = cloudinaryService.uploadFile(file,fileName , "plant");
                // Tạo media và lưu vào database
                Media media = new Media();
                media.setMediaUrl(cloudinaryResponse.getUrl());
                media.setPublicId(cloudinaryResponse.getPublicId());
                media.setMediaType(file.getContentType().contains("image") ? "Image" : "Video");
                media.setEntityType(entityType);
                media.setEntityId(product.getId());
                media.setMediaFormat(file.getContentType());

                mediaService.createMedia(media);
            }
        }
    }

    public ProductResponse updateProduct(String productId, Product product, List<MultipartFile> mediaFiles) {
        product.setId(productId);
        Product saveCategory = productRepository.save(product);
        List<String> urlMedias = mediaService.getMedias(entityType,saveCategory.getId()).stream().map(Media::getMediaUrl).toList();
        ProductResponse response =productMapper.toProductResponse(saveCategory);
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            mediaService.deleteMedia(entityType,productId);
            uploadFiles(entityType,saveCategory,mediaFiles);
            urlMedias = mediaService.getMedias(entityType,saveCategory.getId()).stream().map(Media::getMediaUrl).toList();
            response.setUrlMedias(urlMedias);
        }
        return response;
    }

    public ProductResponse getProduct(String productId) {
        List<String> urlMedias = mediaService.getMedias(entityType,productId).stream().map(Media::getMediaUrl).toList();
        ProductResponse response =productMapper.toProductResponse(productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
        response.setUrlMedias(urlMedias);
        return response;
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
        mediaService.deleteMedia(entityType,productId);
    }
    public Page<ProductResponse> getTopSellingProducts(int page, int size) {
        Pageable top100Pageable = PageRequest.of(0, 100); // Lấy tối đa 100 sản phẩm
        List<Product> top100 = productRepository
                .findAllByIsHiddenFalseAndIsDeletedFalseOrderBySoldQuantityDesc(top100Pageable)
                .getContent(); // Lấy danh sách top 100

        // Tự phân trang từ danh sách 100 đó
        int start = Math.min(page * size, top100.size());
        int end = Math.min(start + size, top100.size());
        List<Product> pagedProducts = top100.subList(start, end);

        return new PageImpl<>(
                pagedProducts.stream().map(productMapper::toProductResponse).toList(),
                PageRequest.of(page, size),
                top100.size()
        );
    }

}
