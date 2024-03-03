package com.photos.backup.service.microservices;

import com.photos.backup.dto.LoadingMetadataDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
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

        try{
            HttpEntity<LoadingMetadataDTO> entity = new HttpEntity<>(dto,headers);
            restTemplate.postForEntity(microServiceUrl, entity, String.class);
        }catch (RestClientException restClientException){
            System.out.println("MICROSERVICE_NOT_RUNNING");
        }
    }
}
