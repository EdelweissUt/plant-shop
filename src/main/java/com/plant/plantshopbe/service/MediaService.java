package com.plant.plantshopbe.service;

import com.plant.plantshopbe.entity.Media;
import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import com.plant.plantshopbe.repository.MediaRepository;
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
public class MediaService {
    MediaRepository mediaRepository;
    CloudinaryService cloudinaryService;
    public Media getAvatar (String entityType,String entityId){
        return mediaRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .orElseThrow(()-> new AppException(ErrorCode.MEDIA_NOT_EXISTED));
    }
    public List<Media> getMedias (String entityType,String entityId){
        return mediaRepository.findAllByEntityTypeAndEntityId(entityType, entityId)
                .orElseThrow(()-> new AppException(ErrorCode.MEDIA_NOT_EXISTED));
    }
    public Media createMedia (Media media){
        return mediaRepository.save(media);
    }

    public void deleteMedia (String entityType,String entityId){
       List<Media> medias = mediaRepository.findAllByEntityTypeAndEntityId(entityType, entityId)
                .orElseThrow(()-> new AppException(ErrorCode.MEDIA_NOT_EXISTED));
       for (Media media :medias){
           mediaRepository.deleteById(media.getId());
           cloudinaryService.deleteFile(media.getPublicId());
       }
    }

}
