package com.photos.backup.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PhotoFile {
    @NotNull
    MultipartFile image;

//    @NotNull
    Date createdDate;

    Double latitude;

    Double longitude;

    String caption;

    @Override
    public String toString() {
        return "PhotoFile{" +
                "image=" + image +
                ", createdDate=" + createdDate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", caption='" + caption + '\'' +
                '}';
    }
}
