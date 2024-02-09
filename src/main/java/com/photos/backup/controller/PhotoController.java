package com.photos.backup.controller;


import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.pojo.PaginationResponse;
import com.photos.backup.service.PhotosService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private PhotosService photosService;
    @PostMapping
    private ResponseEntity<Photo> upload(@RequestParam(PhotoConstants.REQUEST_PARAM_NAME) MultipartFile image, @RequestParam("user") String userId) throws IOException {
        return new ResponseEntity<>(photosService.save(image,userId), HttpStatus.CREATED);
    }

    @GetMapping("/metadata/{photoId}")
    private ResponseEntity<Photo> metadata(@PathVariable("photoId") String photoId,@RequestParam String userId) {
        return new ResponseEntity<>(photosService.get(photoId,userId),HttpStatus.OK);
    }

    @GetMapping("/metadata/all/{userId}")
    private ResponseEntity<PaginationResponse<Photo>> getAllPhotosForUser(@PathVariable("userId") String userId, @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(photosService.getAllForUser(userId,page),HttpStatus.OK);
    }
}
