package com.photos.backup.service;

import com.photos.backup.entity.Photo;
import com.photos.backup.pojo.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotosService {
    Photo save(MultipartFile file,String userId) throws IOException;
    Photo get(String id,String userId);
    void delete(String id);

    PaginationResponse<Photo> getAllForUser(String userId, int page);

}
