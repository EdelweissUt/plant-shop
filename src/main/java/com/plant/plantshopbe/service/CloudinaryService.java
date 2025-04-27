package com.plant.plantshopbe.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.plant.plantshopbe.dto.response.CloudinaryResponse;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.plant.plantshopbe.util.FileUploadUtil.*;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(final MultipartFile file, final String fileName, final String folder) {
        try {
            String mediaType = getMediaType(file);
            if (mediaType.equals("Image")) {
                assertAllowed(file, IMAGE_PATTERN);
            } else if (mediaType.equals("Video")) {
                assertAllowed(file, VIDEO_PATTERN);
            } else {
                throw new AppException(ErrorCode.FILE_NOT_SUPPORT);
            }
            String publicId = "Home/" + folder + "/" + fileName;
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", publicId,
                    "resource_type", "auto"
            ));

            return CloudinaryResponse.builder()
                    .url((String) result.get("secure_url"))
                    .publicId((String) result.get("public_id"))
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.UPLOAD_FAIL);
        }
    }

    @Transactional
    public void deleteFile(final String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap(
                    "resource_type", "image", // nếu file là ảnh
                    "invalidate", true
            ));

            // Nếu không tìm thấy hoặc không xoá được
            if (!"ok".equals(result.get("result"))) {
                // Thử lại với video (nếu là video)
                result = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap(
                        "resource_type", "video",
                        "invalidate", true
                ));

                if (!"ok".equals(result.get("result"))) {
                    throw new AppException(ErrorCode.DELETE_FILE_FAIL);
                }
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Transactional
    public CloudinaryResponse updateFile(final MultipartFile newFile, final String oldPublicId, final String newFileName, final String folder) {
        deleteFile(oldPublicId);
        return uploadFile(newFile, newFileName, folder);
    }
}