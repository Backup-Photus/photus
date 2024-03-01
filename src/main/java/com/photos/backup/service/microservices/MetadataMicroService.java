package com.photos.backup.service.microservices;

import com.photos.backup.dto.LoadingMetadataDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class MetadataMicroService extends Thread{

    private static final String microServiceUrl = "http://localhost:9090/metadata";
    String userId;
    String photoId;
    String path;

    @Override
    public void run() {
        RestTemplate restTemplate =  new RestTemplate();

        LoadingMetadataDTO dto = LoadingMetadataDTO.builder()
                .userId(userId)
                .photoId(photoId)
                .path(path).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoadingMetadataDTO> entity = new HttpEntity<>(dto,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(microServiceUrl, entity, String.class);
        if(response.getStatusCode().value()!=200){
            System.out.println(response.getBody());
        }
    }
}
