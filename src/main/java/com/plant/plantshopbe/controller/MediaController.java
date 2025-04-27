package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.dto.response.CloudinaryResponse;
import com.plant.plantshopbe.service.CloudinaryService;
import com.plant.plantshopbe.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final CloudinaryService cloudinaryService;

    // API upload media
    @PostMapping("/upload")
    public ResponseEntity<CloudinaryResponse> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("folder") String folder
    ) {
        // Generate file name
        String fileName = FileUploadUtil.getFileName(name);
        // Upload file and get response
        CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName, folder);
        // Return the response
        return ResponseEntity.ok(response);
    }

    // API delete media by publicId
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMedia(@RequestParam("publicId") String publicId) {
        try {
            // Call service to delete file
            cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok("Xóa media thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Xóa media thất bại: " + e.getMessage());
        }
    }

    // API update media (delete old + upload new)
    @PutMapping("/update")
    public ResponseEntity<CloudinaryResponse> updateMedia(
            @RequestParam("oldPublicId") String oldPublicId,
            @RequestParam("file") MultipartFile newFile,
            @RequestParam("name") String name,
            @RequestParam("folder") String folder
    ) {
        // Generate new file name
        String fileName = FileUploadUtil.getFileName(name);
        // Call service to update the file (delete old + upload new)
        CloudinaryResponse response = cloudinaryService.updateFile(newFile, oldPublicId, fileName, folder);
        return ResponseEntity.ok(response);
    }
}