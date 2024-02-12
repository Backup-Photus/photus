package com.photos.backup.controller;


import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import com.photos.backup.service.PhotosService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private ResponseEntity<PhotoDTO> upload(@RequestParam(PhotoConstants.REQUEST_PARAM_NAME) MultipartFile image, @RequestParam("user") String userId) throws IOException {
        return new ResponseEntity<>(photosService.save(image,userId), HttpStatus.CREATED);
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
    private ResponseEntity<PhotoDTO> metadata(@PathVariable String photoId,@RequestParam String userId) {
        return new ResponseEntity<>(photosService.getMetadata(photoId,userId),HttpStatus.OK);
    }

    @GetMapping("/metadata/all/{userId}")
    private ResponseEntity<PhotosPaginationDTO<PhotoDTO>> getAllPhotosForUser(@PathVariable String userId, @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(photosService.getMetadataAllForUser(userId,page),HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    private ResponseEntity<HttpStatus> deletePhoto(@PathVariable String photoId){
        photosService.delete(photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
