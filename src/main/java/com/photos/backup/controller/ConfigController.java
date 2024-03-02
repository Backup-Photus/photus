package com.photos.backup.controller;


import com.photos.backup.dto.ResponseDTO;
import com.photos.backup.repository.SystemConfigsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@AllArgsConstructor
public class ConfigController {

    private SystemConfigsRepository configsRepository;

    @GetMapping
    private ResponseEntity<ResponseDTO<?>>get() {
        return new ResponseEntity<>(ResponseDTO.noErrorResponse(configsRepository),HttpStatus.OK);
    }
}
