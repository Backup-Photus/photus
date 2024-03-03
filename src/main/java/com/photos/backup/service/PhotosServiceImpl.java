package com.photos.backup.service;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import com.photos.backup.entity.Metadata;
import com.photos.backup.entity.Photo;
import com.photos.backup.entity.User;
import com.photos.backup.exception.PhotosException;
import com.photos.backup.exception.PhotosException.PhotosExceptions;
import com.photos.backup.repository.*;
import com.photos.backup.service.microservices.MetadataMicroService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.photos.backup.utils.ConversionHelperUtil.fromString;

@Service
@AllArgsConstructor
public class PhotosServiceImpl implements PhotosService {

    PhotosRepository photosRepository;
    UserRepository userRepository;
    DirRepository dirRepository;
    MetadataRepository metadataRepository;
    SystemConfigsRepository systemConfigsRepository;

    @Override
    public PhotoDTO save(MultipartFile file, String userId,String hostname) throws IOException {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(fromString(userId)),userId);
        Photo photoFile =  dirRepository.savePhoto(user.getId(),file);
        photoFile.setUser(user);
        photoFile = photosRepository.save(photoFile);
        saveMetadata(photoFile.getId().toString(),userId,photoFile.getPath());
        return new PhotoDTO(photoFile,getDownloadBaseUrl(hostname),getThumbnailBaseUrl(hostname));
    }

    @Override
    public PhotoDTO getMetadata(String photoId, String userId,String hostname) {
        Photo photo =  unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        if(!photo.getUser().getId().toString().equals(userId))
            throw new PhotosException(PhotosExceptions.NOT_AUTHORISED_TO_ACCESS,photoId);
        Metadata metadata = unwrapMetadata(metadataRepository.findById(fromString(photoId)),photoId);
        PhotoDTO dto = new PhotoDTO(photo,getDownloadBaseUrl(hostname),getThumbnailBaseUrl(hostname));
        return dto.toBuilder().fileMetadata(metadata).build();
    }

    @Override
    public void delete(String photoId,String userId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        if(!photo.getUser().getId().toString().equals(userId))
            throw new PhotosException(PhotosExceptions.NOT_AUTHORISED_TO_ACCESS,photoId);
        dirRepository.delete(photo.getPath());
        photosRepository.deleteById(fromString(photoId));
    }

    @Override
    public PhotosPaginationDTO<PhotoDTO> getMetadataAllForUser(String userId, String hostname,int page) {
        PageRequest pageRequest= PageRequest.of(page, PhotoConstants.PAGE_SIZE);
        Page<Photo> photos = photosRepository.findAllByUserId(fromString(userId),pageRequest);
        List<PhotoDTO> content = photos.getContent().stream().map(e->new PhotoDTO(e,getDownloadBaseUrl(hostname),getThumbnailBaseUrl(hostname))).toList();
        PhotosPaginationDTO.PhotosPaginationDTOBuilder<PhotoDTO> responseBuilder = PhotosPaginationDTO.<PhotoDTO>builder()
                .pageSize(PhotoConstants.PAGE_SIZE)
                .data(content)
                .currentPage(page)
                .isEndPage(photos.isLast());
        if(!photos.isLast()) {
            responseBuilder = responseBuilder
                    .nextPage((long) (page + 1))
                    .nextPageLink(createNextPageLink(userId,hostname,page));

        }
        return responseBuilder.build();
    }

    @Override
    public File get(String photoId,String userId) {
        Photo photo =  unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        if(!photo.getUser().getId().toString().equals(userId))
            throw new PhotosException(PhotosExceptions.NOT_AUTHORISED_TO_ACCESS,photoId);
        return dirRepository.read(photo.getPath());
    }

    @Override
    public File getThumbnail(String photoId, String userId) {
        Photo photo = unwrapPhoto(photosRepository.findById(fromString(photoId)),photoId);
        if(!photo.getUser().getId().toString().equals(userId))
            throw new PhotosException(PhotosExceptions.NOT_AUTHORISED_TO_ACCESS,photoId);
        return dirRepository.read(photo.getThumbnailPath());
    }

    public static Photo unwrapPhoto(Optional<Photo> photo, String photoId){
        if(photo.isPresent()) return photo.get();
        else throw  new PhotosException(PhotosExceptions.PHOTO_NOT_FOUND,photoId);
    }

    public static Metadata unwrapMetadata(Optional<Metadata>data ,String photoId){
        if(data.isPresent()) return data.get();
        else throw new PhotosException(PhotosExceptions.PHOTO_NO_METADATA_AVAILABLE,photoId);
    }
    private String createNextPageLink(String userId,String hostname,int page){
        int nextPage=page+1;
        return hostname +"/all/"+userId+"?page="+nextPage;
    }

    private void saveMetadata(String photoId,String userId,String photoPath){
        new MetadataMicroService(userId,photoId,photoPath, systemConfigsRepository.getExtractorMicroservice()).start();
    }
    private String getThumbnailBaseUrl(String hostname){
        return hostname + "/photo/" ;
    }
    private  String getDownloadBaseUrl(String hostname){
        return  hostname + "/photo/thumbnail/";
    }
}
