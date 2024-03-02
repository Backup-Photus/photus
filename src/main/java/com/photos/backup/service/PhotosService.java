package com.photos.backup.service;

import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PhotosService {
    PhotoDTO save(MultipartFile file, String userId) throws IOException;
    PhotoDTO getMetadata(String photoId, String userId);
    void delete(String photoId,String userId);
    PhotosPaginationDTO<PhotoDTO> getMetadataAllForUser(String userId, int page);
    File get(String photoId,String userId);

    File getThumbnail(String photoId,String userId);


}
