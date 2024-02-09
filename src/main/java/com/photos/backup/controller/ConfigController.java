package com.photos.backup.controller;


import com.photos.backup.repository.SystemConfigsRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@AllArgsConstructor
public class ConfigController {

    private SystemConfigsRepository configsRepository;

    @GetMapping
    private SystemConfigsRepository get(@RequestParam("variable") String variable) {
        return configsRepository;
    }
}
