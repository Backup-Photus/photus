package com.photos.backup.dto;

import lombok.Builder;

@Builder
public record LoadingMetadataDTO(String userId,String photoId,String path) {
}
