package com.photos.backup.dto;

import com.photos.backup.entity.Photo;
import com.photos.backup.pojo.GeoLocation;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record PhotoDTO(
        UUID id,
        String originalName,
        long size,
        GeoLocation location,
        String caption,
        Date uploadDate,
        String downloadLink,
        String thumbnailLink) {
    public PhotoDTO(Photo photo,String downloadBaseUrl,String thumbnailBaseUrl){
        this(
                photo.getId(),
                photo.getOriginalName(),
                photo.getSize(),
                photo.getLocation(),
                photo.getCaption(),
                photo.getUploadDate(),
                downloadBaseUrl +   photo.getId(),
                thumbnailBaseUrl +   photo.getId()
                );
    }
}
