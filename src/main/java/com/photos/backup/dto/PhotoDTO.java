package com.photos.backup.dto;

import com.photos.backup.entity.Metadata;
import com.photos.backup.entity.Photo;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder(toBuilder = true)
public record PhotoDTO(
        UUID id,
        String originalName,
        long size,
        String caption,
        Date uploadDate,
        String downloadLink,
        String thumbnailLink,
        Metadata fileMetadata
)  {
    public PhotoDTO(Photo photo, String downloadBaseUrl,String thumbnailBaseUrl){
        this(
                photo.getId(),
                photo.getOriginalName(),
                photo.getSize(),
                photo.getCaption(),
                photo.getUploadDate(),
                downloadBaseUrl +   photo.getId(),
                thumbnailBaseUrl +   photo.getId(),
                null
                );
    }
    public PhotoDTO(Photo photo, Metadata metadata, String downloadBaseUrl, String thumbnailBaseUrl){
        this(
                photo.getId(),
                photo.getOriginalName(),
                photo.getSize(),
                photo.getCaption(),
                photo.getUploadDate(),
                downloadBaseUrl +   photo.getId(),
                thumbnailBaseUrl +   photo.getId(),
                metadata
        );
    }
}
