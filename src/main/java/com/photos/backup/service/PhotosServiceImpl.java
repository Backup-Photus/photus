package com.photos.backup.service;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import com.photos.backup.entity.Photo;
import com.photos.backup.entity.User;
import com.photos.backup.exception.PhotosException;
import com.photos.backup.exception.PhotosException.PhotosExceptions;
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
    public PhotoDTO save(MultipartFile file, String userId) throws IOException {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(fromString(userId)),userId);
        Photo photoFile =  dirRepository.savePhoto(user.getId(),file);
        photoFile.setUser(user);
        photosRepository.save(photoFile);
        return new PhotoDTO(photoFile,getDownloadBaseUrl(),getThumbnailBaseUrl());
    }

    @Override
    public PhotoDTO getMetadata(String photoId, String userId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        return new PhotoDTO(photo,getDownloadBaseUrl(),getThumbnailBaseUrl());
    }

    @Override
    public void delete(String photoId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        dirRepository.delete(photo.getPath());
        photosRepository.deleteById(fromString(photoId));
    }

    @Override
    public PhotosPaginationDTO<PhotoDTO> getMetadataAllForUser(String userId, int page) {
        PageRequest pageRequest= PageRequest.of(page, PhotoConstants.PAGE_SIZE);
        Page<Photo> photos = photosRepository.findAllByUserId(fromString(userId),pageRequest);
        List<PhotoDTO> content = photos.getContent().stream().map(e->new PhotoDTO(e,getDownloadBaseUrl(),getThumbnailBaseUrl())).toList();
        PhotosPaginationDTO.PhotosPaginationDTOBuilder<PhotoDTO> responseBuilder = PhotosPaginationDTO.<PhotoDTO>builder()
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

    private String getThumbnailBaseUrl(){
        return systemConfigsRepository.getBaseUrlWithPort() + "/photo/" ;
    }
    private  String getDownloadBaseUrl(){
        return systemConfigsRepository.getBaseUrlWithPort() + "/photo/thumbnail/";
    }
}
