package com.photos.backup.utils;

import com.photos.backup.constants.FileOperationConstants;
import com.photos.backup.constants.ImageType;
import com.photos.backup.exception.FileOperationsExceptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ConversionHelperUtil {

    public static File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }


    public static UUID fromString(String uuid){
        return UUID.fromString(uuid);
    }

    public static String fileExtension(String mimeType){
        for (ImageType imageType : ImageType.values()) {
            if (imageType.getMimeType().equals(mimeType)) {
                return imageType.getFileExtension();
            }
        }
        throw new FileOperationsExceptions(FileOperationConstants.NO_SUCH_FILE_TYPE);
    }
}
