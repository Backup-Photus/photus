package com.photos.backup.controller;


import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.pojo.PaginationResponse;
import com.photos.backup.service.PhotosService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private PhotosService photosService;
    @PostMapping
    private ResponseEntity<Photo> upload(@RequestParam(PhotoConstants.REQUEST_PARAM_NAME) MultipartFile image, @RequestParam("user") String userId) throws IOException {
        return new ResponseEntity<>(photosService.save(image,userId), HttpStatus.CREATED);
    }

    @GetMapping("/{photoId}")
    private ResponseEntity<InputStreamResource> get(@PathVariable String photoId,@RequestParam String userId) throws IOException {
        File file = photosService.get(photoId);
        Photo photo = photosService.getMetadata(photoId,userId);
        InputStream inputStream = new FileInputStream(file);

        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(Paths.get(file.getPath()))));
        headers.setContentDispositionFormData("attachment", photo.getOriginalName());

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/thumbnail/{photoId}")
    private String getThumbnail(@PathVariable String photoId,@RequestParam String userId){
        return "THUMBNAIL_CREATED";
    }
    @GetMapping("/metadata/{photoId}")
    private ResponseEntity<Photo> metadata(@PathVariable String photoId,@RequestParam String userId) {
        return new ResponseEntity<>(photosService.getMetadata(photoId,userId),HttpStatus.OK);
    }

    @GetMapping("/metadata/all/{userId}")
    private ResponseEntity<PaginationResponse<Photo>> getAllPhotosForUser(@PathVariable String userId, @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(photosService.getMetadataAllForUser(userId,page),HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    private ResponseEntity<HttpStatus> deletePhoto(@PathVariable String photoId){
        photosService.delete(photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
