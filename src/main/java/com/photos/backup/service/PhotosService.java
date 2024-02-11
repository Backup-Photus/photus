package com.photos.backup.service;

import com.photos.backup.entity.Photo;
import com.photos.backup.pojo.PaginationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PhotosService {
    Photo save(MultipartFile file,String userId) throws IOException;
    Photo getMetadata(String id, String userId);
    void delete(String id);
    PaginationResponse<Photo> getMetadataAllForUser(String userId, int page);
    File get(String photoId,String userId);

    File getThumbnail(String photoId,String userId);


}
