package com.photos.backup.service;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.entity.User;
import com.photos.backup.exception.PhotoNotFoundException;
import com.photos.backup.pojo.PaginationResponse;
import com.photos.backup.repository.DirRepository;
import com.photos.backup.repository.PhotosRepository;
import com.photos.backup.repository.SystemConfigsRepository;
import com.photos.backup.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static com.photos.backup.utils.ConversionHelperUtil.fromString;

@Service
@AllArgsConstructor
public class PhotosServiceImpl implements PhotosService {

    PhotosRepository photosRepository;
    UserRepository userRepository;
    DirRepository dirRepository;
    SystemConfigsRepository systemConfigsRepository;

    @Override
    public Photo save(MultipartFile file,String userId) throws IOException {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(fromString(userId)),userId);
        Photo photoFile =  dirRepository.save(user.getId(),file);
        photoFile.setUser(user);
        photosRepository.save(photoFile);
        return photoFile;
    }

    @Override
    public Photo getMetadata(String photoId, String userId) {
        return unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
    }

    @Override
    public void delete(String photoId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        dirRepository.delete(photo.getPath());
        photosRepository.deleteById(fromString(photoId));
    }

    @Override
    public PaginationResponse<Photo> getMetadataAllForUser(String userId, int page) {
        PageRequest pageRequest= PageRequest.of(page, PhotoConstants.PAGE_SIZE);
        Page<Photo> photos = photosRepository.findAllByUserId(fromString(userId),pageRequest);
        for(Photo photo:photos.getContent()){
            photo.setDownloadLink(createDownloadLink(photo.getId()));
            photo.setThumbnailLink(createThumbnailLink(photo.getId()));
        }
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

    @Override
    public File get(String photoId) {
        Photo photo =  unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        return dirRepository.read(photo.getPath());
    }

    public static Photo unwrapPhoto(Optional<Photo> photo, String photoId){
        if(photo.isPresent()) return  photo.get();
        else throw  new PhotoNotFoundException(photoId);
    }

    private String createNextPageLink(String userId,int page){
        int nextPage=page+1;
        String baseUrl= systemConfigsRepository.getBaseUrlWithPort();
        return baseUrl+"/all/"+userId+"?page="+nextPage;
    }

    private String createDownloadLink(UUID uuid){
        return systemConfigsRepository.getBaseUrlWithPort() + "/" + Paths.get("photo")
                .resolve(uuid.toString());
    }
    private String createThumbnailLink(UUID uuid){
        return systemConfigsRepository.getBaseUrlWithPort() + "/" + Paths.get("photo"  ,"thumbnail",  uuid.toString());
    }
}
