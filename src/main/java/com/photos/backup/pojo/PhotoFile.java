package com.photos.backup.pojo;

import io.micrometer.common.lang.Nullable;
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

    @Nullable
    Date createdDate;

    @Nullable
    String caption;
}
