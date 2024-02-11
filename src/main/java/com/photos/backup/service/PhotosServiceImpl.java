package com.photos.backup.service;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.entity.User;
import com.photos.backup.exception.PhotosException;
import com.photos.backup.exception.PhotosException.PhotosExceptions;
import com.photos.backup.pojo.PaginationResponse;
import com.photos.backup.pojo.PaginationResponse.PaginationResponseBuilder;
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
import java.util.ArrayList;
import java.util.List;
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
        Photo photoFile =  dirRepository.savePhoto(user.getId(),file);
        photoFile.setUser(user);
        photosRepository.save(photoFile);
        return photoFile;
    }

    @Override
    public Photo getMetadata(String photoId, String userId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        return addLinks(photo);
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
        List<Photo> content = new ArrayList<>(photos.getContent());
        for(int index=0;index<photos.getContent().size();index++){
            content.set(index,addLinks(content.get(index)));
        }
        PaginationResponseBuilder<Photo> responseBuilder = PaginationResponse.<Photo>builder()
                .pageSize(PhotoConstants.PAGE_SIZE)
                .data(content)
                .currentPage(page)
                .isEndPage(photos.isLast());
        if(!photos.isLast()) {
            responseBuilder = responseBuilder
                    .nextPage((long) (page + 1))
                    .nextPageLink(createNextPageLink(userId,page));

        }
        return responseBuilder.build();
    }

    @Override
    public File get(String photoId,String userId) {
        Photo photo =  unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        return dirRepository.read(photo.getPath());
    }

    @Override
    public File getThumbnail(String photoId, String userId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        return dirRepository.read(photo.getThumbnailPath());
    }

    public static Photo unwrapPhoto(Optional<Photo> photo, String photoId){
        if(photo.isPresent()) return photo.get();
        else throw  new PhotosException(PhotosExceptions.PHOTO_NOT_FOUND,photoId);
    }

    private String createNextPageLink(String userId,int page){
        int nextPage=page+1;
        String baseUrl= systemConfigsRepository.getBaseUrlWithPort();
        return baseUrl+"/all/"+userId+"?page="+nextPage;
    }

    private  Photo addLinks(Photo photo){
        photo.setDownloadLink(createDownloadLink(photo.getId()));
        photo.setThumbnailLink(createThumbnailLink(photo.getId()));
        return photo;
    }
    private String createDownloadLink(UUID uuid){
        return systemConfigsRepository.getBaseUrlWithPort() + "/" + Paths.get("photo")
                .resolve(uuid.toString());
    }
    private String createThumbnailLink(UUID uuid){
        return systemConfigsRepository.getBaseUrlWithPort() + "/" + Paths.get("photo"  ,"thumbnail",  uuid.toString());
    }
}
