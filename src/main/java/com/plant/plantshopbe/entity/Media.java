package com.plant.plantshopbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, length = 500)
    String mediaUrl;  // URL hoặc đường dẫn ảnh/video từ Cloudinary

    String publicId;  // ID trên Cloudinary, để xóa hoặc cập nhật

    String mediaType;  // "Image", "Video"

    String entityType;  // Ví dụ: "Product", "User", "Category"

    String entityId;  // ID thực thể tương ứng (VD: productId)

    String mediaFormat;  // Ví dụ: "image/jpeg", "video/mp4"

    @CreationTimestamp
    LocalDateTime createdAt;
}
