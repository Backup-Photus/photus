package com.photos.backup.service;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.entity.User;
import com.photos.backup.exception.PhotoNotFoundException;
import com.photos.backup.pojo.PaginationResponse;
import com.photos.backup.repository.PhotosRepository;
import com.photos.backup.repository.UserRepository;
import com.photos.backup.utils.ConversionHelperUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


import static com.photos.backup.utils.ConversionHelperUtil.fromString;

@Service
@AllArgsConstructor
public class PhotosServiceImpl implements PhotosService {

    PhotosRepository photosRepository;

    UserRepository userRepository;
    @Override
    public Photo save(MultipartFile file,String userId) throws IOException {
        File photoFile = ConversionHelperUtil.convertMultiPartToFile(file);
        User user = UserServiceImpl.unwrapUser(userRepository.findById(fromString(userId)),userId);
        Photo photo = new Photo(photoFile,user);
        photosRepository.save(photo);
        return photo;
    }

    @Override
    public Photo get(String photoId,String userId) {
        return unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
    }

    @Override
    public void delete(String photoId) {
    photosRepository.deleteById(fromString(photoId));
    }

    @Override
    public PaginationResponse<Photo> getAllForUser(String userId,int page) {
        PageRequest pageRequest= PageRequest.of(page, PhotoConstants.PAGE_SIZE);
        Page<Photo> photos = photosRepository.findAllByUserId(fromString(userId),pageRequest);
        PaginationResponse.Builder<Photo> responseBuilder = new PaginationResponse.Builder<Photo>()
                .setPageSize(PhotoConstants.PAGE_SIZE)
                .setData(photos.getContent())
                .setCurrentPage(page)
                .setIsEndPage(photos.isLast());
        if(!photos.isLast()) {
            responseBuilder = responseBuilder
                    .setNextPage(page + 1)
                    .setNextPageLink(createNextPageLink(userId,page));

        }
        return responseBuilder.build();
    }

    public static Photo unwrapPhoto(Optional<Photo> photo, String photoId){
        if(photo.isPresent()) return  photo.get();
        else throw  new PhotoNotFoundException(photoId);
    }

    private String createNextPageLink(String userId,int page){
        int nextPage=page+1;
        String baseUrl="localhost:8080";
        return baseUrl+"/all/"+userId+"?page="+nextPage;
    }
}
