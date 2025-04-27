package com.plant.plantshopbe.service;

import com.plant.plantshopbe.dto.response.CategoryResponse;
import com.plant.plantshopbe.entity.Category;
import com.plant.plantshopbe.entity.Media;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.mapper.CategoryMapper;
import com.plant.plantshopbe.repository.CategoryRepository;
import com.plant.plantshopbe.repository.MediaRepository;
import com.plant.plantshopbe.util.FileUploadUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    MediaService mediaService ;
    CloudinaryService cloudinaryService;
    CategoryMapper categoryMapper;
    String entityType ="Category";
    public List<CategoryResponse> getCategories() {
        List<CategoryResponse> responses = categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
        responses.forEach(categoryResponse -> {
            List<String> urls = mediaService
                    .getMedias(entityType, categoryResponse.getId())
                    .stream()
                    .map(Media::getMediaUrl)
                    .toList();
            categoryResponse.setUrlMedias(urls);
        });
        return responses;
    }

    public CategoryResponse getCategory(String categoryId) {
        List<String> urlMedias = mediaService.getMedias(entityType,categoryId).stream().map(Media::getMediaUrl).toList();
        CategoryResponse response =categoryMapper.toCategoryResponse(categoryRepository.findById(categoryId).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED)));
        response.setUrlMedias(urlMedias);
        return response;
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
        mediaService.deleteMedia(entityType,categoryId);
    }

    public CategoryResponse createCategory(Category category, List<MultipartFile> mediaFiles) {
        Category saveCategory = categoryRepository.save(category);
        // Xử lý media files
        uploadFiles(entityType,saveCategory,mediaFiles);
        List<String> urlMedias = mediaService.getMedias(entityType,saveCategory.getId()).stream().map(Media::getMediaUrl).toList();
        CategoryResponse response =categoryMapper.toCategoryResponse(saveCategory);
        response.setUrlMedias(urlMedias);
        return response;
    }

    public CategoryResponse updateCategory(String categoryId, Category category, List<MultipartFile> mediaFiles) {
        category.setId(categoryId);
        Category saveCategory = categoryRepository.save(category);
        List<String> urlMedias = mediaService.getMedias(entityType,saveCategory.getId()).stream().map(Media::getMediaUrl).toList();
        CategoryResponse response =categoryMapper.toCategoryResponse(saveCategory);
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            mediaService.deleteMedia(entityType,categoryId);
            uploadFiles(entityType,saveCategory,mediaFiles);
            urlMedias = mediaService.getMedias(entityType,saveCategory.getId()).stream().map(Media::getMediaUrl).toList();
            response.setUrlMedias(urlMedias);
        }
        return response;
    }
    public void uploadFiles(String entityType,Category category, List<MultipartFile> mediaFiles){
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            for (MultipartFile file : mediaFiles) {
                // Upload file lên Cloudinary
                String fileName = FileUploadUtil.getFileName(category.getName());
                var cloudinaryResponse = cloudinaryService.uploadFile(file,fileName , "plant");

                // Tạo media và lưu vào database
                Media media = new Media();
                media.setMediaUrl(cloudinaryResponse.getUrl());
                media.setPublicId(cloudinaryResponse.getPublicId());
                media.setMediaType(file.getContentType().contains("image") ? "Image" : "Video");
                media.setEntityType(entityType);
                media.setEntityId(category.getId());
                media.setMediaFormat(file.getContentType());

                mediaService.createMedia(media);
            }
        }
    }
}
