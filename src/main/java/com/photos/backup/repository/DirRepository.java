package com.photos.backup.repository;

import com.photos.backup.constants.PhotoConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.exception.FileException;
import com.photos.backup.exception.FileException.FileOperationExceptions;
import com.photos.backup.utils.ConversionHelperUtil;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class DirRepository {

    private SystemConfigsRepository systemConfigsRepository;

    void canSave(long size){
        if(!systemConfigsRepository.isHasWritePermission()) throw  new FileException(FileOperationExceptions.NO_WRITE_ACCESS_IN_DATA_DIR);

    }

    void canRead(Path path){
        if(!systemConfigsRepository.isHasReadPermission()) throw  new FileException(FileOperationExceptions.NO_READ_ACCESS_IN_DATA_DIR);
    }

    public Photo savePhoto(UUID userId , MultipartFile file) throws IOException {
        canSave(file.getSize());
        Date today =  new Date(System.currentTimeMillis());
        Path dirPath = getDirectoryForFileSaving(userId);
        UUID uuid = systemConfigsRepository.getNewID();

        String fileExtension = ConversionHelperUtil.fileExtension(file.getContentType());
        String filename = uuid + fileExtension;

        File savingDirectory = createDirectory(dirPath);
        File savingFile = createFile(savingDirectory.toPath().resolve(filename));
        file.transferTo(savingFile);

        Path thumbnailPath = saveThumbnail(savingFile,userId,filename);

    return Photo.builder()
            .id(uuid)
            .size(file.getSize())
            .originalName(file.getOriginalFilename())
            .path(savingFile.getPath())
            .thumbnailPath(thumbnailPath.toString())
            .creationDate(today)
            .uploadDate(today)
            .build();
    }

    public Path saveThumbnail(File file,UUID userId,String photoName) throws IOException {
        Path dir = getDirectoryForFileSaving(userId);
        File thumbnailDir = createDirectory(dir.resolve(PhotoConstants.THUMBNAIL_DIRECTORY_NAME));
        Path photoThumbnailPath = thumbnailDir.toPath().resolve(photoName);
        Thumbnails.of(file)
                .size(PhotoConstants.THUMBNAIL_SIZE_X, PhotoConstants.THUMBNAIL_SIZE_Y)
                .outputQuality(0.8).toFile(photoThumbnailPath.toString());
        return photoThumbnailPath;
    }
    public File update(String path,File file){
        return  null;
    }

    public File read(String path){
        Path filepath =  Paths.get(path);
        canRead(filepath);
        File file = new File(filepath.toUri());
        if(file.exists()) return  file;
        throw  new FileException(FileOperationExceptions.NO_FILE_EXIST);
    }

    public void delete(String path){
        Path filepath =  Paths.get(path);
        canRead(filepath);
        File file = new File(filepath.toUri());
        if (!file.exists() || !file.delete()) {
            throw  new FileException(FileOperationExceptions.NO_FILE_EXIST);
        }
    }

    private Path getDirectoryForFileSaving(UUID userId){
        Path directoryPath = Paths
                .get(systemConfigsRepository.getDataDirPath())
                .resolve(userId.toString())
                .resolve(systemConfigsRepository.getTodayDate());
        return directoryPath.toAbsolutePath();
    }

    private File createDirectory(Path dirPath){
        File directory = new File(dirPath.toUri());
        boolean created = directory.isDirectory() && directory.exists();
        if(!created) created = directory.mkdirs();
        if(!created)
            throw  new FileException(FileOperationExceptions.UNKNOWN_DIR_EXCEPTION);
        return directory;
    }

    private File createFile(Path filePath) throws IOException {
        File file = new File(filePath.toUri());
        boolean created = file.isFile() && file.exists();
        if(!created) created = file.createNewFile();
        if(!created) throw new FileException(FileOperationExceptions.UNKNOWN_FILE_EXCEPTION);
        return file;
    }
}
