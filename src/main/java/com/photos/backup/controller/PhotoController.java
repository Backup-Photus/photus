package com.photos.backup.controller;


import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import com.photos.backup.pojo.ErrorFreeDTO;
import com.photos.backup.pojo.PhotoFile;
import com.photos.backup.service.PhotosService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private PhotosService photosService;
    @PostMapping
    private ResponseEntity<ErrorFreeDTO<PhotoDTO>> upload(@Valid @ModelAttribute PhotoFile photoFile) throws IOException {
        ErrorFreeDTO<PhotoDTO> freeDTO = new ErrorFreeDTO<>(photosService.save(photoFile.getImage(),photoFile.getUserId()));
        return new ResponseEntity<>(freeDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{photoId}")
    private ResponseEntity<InputStreamResource> get(@PathVariable String photoId,@RequestParam String userId) throws IOException {
        File file = photosService.get(photoId,userId);
        PhotoDTO photo = photosService.getMetadata(photoId,userId);
        InputStream inputStream = new FileInputStream(file);

        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(Paths.get(file.getPath()))));
        headers.setContentDispositionFormData("attachment", photo.originalName());

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/thumbnail/{photoId}")
    private ResponseEntity<InputStreamResource> getThumbnail(@PathVariable String photoId,@RequestParam String userId) throws IOException {
        File file = photosService.getThumbnail(photoId,userId);
        PhotoDTO photo = photosService.getMetadata(photoId,userId);
        InputStream inputStream = new FileInputStream(file);

        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(Paths.get(file.getPath()))));
        headers.setContentDispositionFormData("attachment", photo.originalName());

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }
    @GetMapping("/metadata/{photoId}")
    private ResponseEntity<ErrorFreeDTO<PhotoDTO>> metadata(@PathVariable String photoId,@RequestParam String userId) {
        ErrorFreeDTO<PhotoDTO> freeDTO = new ErrorFreeDTO<>(photosService.getMetadata(photoId,userId));
         return new ResponseEntity<>(freeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/metadata/all/{userId}")
    private ResponseEntity<ErrorFreeDTO<PhotosPaginationDTO<PhotoDTO>>> getAllPhotosForUser(@PathVariable String userId, @RequestParam(defaultValue = "0") int page){
        ErrorFreeDTO<PhotosPaginationDTO<PhotoDTO>> freeDTO = new ErrorFreeDTO<>(photosService.getMetadataAllForUser(userId,page));
        return new ResponseEntity<>(freeDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    private ResponseEntity<ErrorFreeDTO<?>> deletePhoto(@PathVariable String photoId){
        photosService.delete(photoId);
        return new ResponseEntity<>( ErrorFreeDTO.EmptyErrorFreeDTO,HttpStatus.OK);
    }
}
