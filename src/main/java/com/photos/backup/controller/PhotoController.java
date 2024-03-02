package com.photos.backup.controller;


import com.photos.backup.dto.PhotoDTO;
import com.photos.backup.dto.PhotosPaginationDTO;
import com.photos.backup.dto.ResponseDTO;
import com.photos.backup.pojo.PhotoFile;
import com.photos.backup.service.PhotosService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private ResponseEntity<ResponseDTO<PhotoDTO>> upload(@Valid @ModelAttribute PhotoFile photoFile) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseDTO<PhotoDTO> freeDTO = ResponseDTO.noErrorResponse(photosService.save(photoFile.getImage(),userId));
        return new ResponseEntity<>(freeDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{photoId}")
    private ResponseEntity<InputStreamResource> get(@PathVariable String photoId) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
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
    private ResponseEntity<InputStreamResource> getThumbnail(@PathVariable String photoId) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
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
    private ResponseEntity<ResponseDTO<PhotoDTO>> metadata(@PathVariable String photoId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseDTO<PhotoDTO> freeDTO =ResponseDTO.noErrorResponse(photosService.getMetadata(photoId,userId));
         return new ResponseEntity<>(freeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/metadata/all")
    private ResponseEntity<ResponseDTO<PhotosPaginationDTO<PhotoDTO>>> getAllPhotosForUser(@RequestParam(defaultValue = "0") int page){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseDTO<PhotosPaginationDTO<PhotoDTO>> freeDTO = ResponseDTO.noErrorResponse(photosService.getMetadataAllForUser(userId,page));
        return new ResponseEntity<>(freeDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    private ResponseEntity<ResponseDTO<?>> deletePhoto(@PathVariable String photoId){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        photosService.delete(photoId);
        return new ResponseEntity<>(ResponseDTO.EmptyErrorFreeResponse,HttpStatus.OK);
    }
}
