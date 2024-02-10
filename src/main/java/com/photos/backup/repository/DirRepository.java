package com.photos.backup.repository;


import com.photos.backup.constants.FileOperationConstants;
import com.photos.backup.entity.Photo;
import com.photos.backup.exception.FileOperationsExceptions;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class DirRepository {

    private SystemConfigsRepository systemConfigsRepository;

    boolean canSave(long size){
        return  true;
    }

    public Photo save(UUID userId , MultipartFile file) throws IOException {
    Path dirPath = getDirectoryForFileSaving(userId);
    UUID uuid = systemConfigsRepository.getNewID();

    String fileExtension =  Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    String filename = uuid + "." + fileExtension;

    File savingDirectory = createDirectory(dirPath);
    File savingFile = createFile(savingDirectory.toPath().resolve(filename));
    file.transferTo(savingFile);

    return new Photo.Builder()
            .id(uuid)
            .size(file.getSize())
            .originalName(file.getOriginalFilename())
            .path(savingFile.getPath())
            .build();
    }

    public File update(String path,File file){
        return  null;
    }

    public File read(String path){
        return  null;
    }

    public void delete(String path){
        
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
            throw  new FileOperationsExceptions(FileOperationConstants.UNKNOWN_DIR_EXCEPTION);
        return directory;
    }

    private File createFile(Path filePath) throws IOException {
        File file = new File(filePath.toUri());
        boolean created = file.isFile() && file.exists();
        if(!created) created = file.createNewFile();
        if(!created) throw new FileOperationsExceptions(FileOperationConstants.UNKNOWN_FILE_EXCEPTION);
        return file;
    }

    private boolean cloneFile(File sourceFile,File destinationFile)
    {
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File copied successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
            return false;
        }
    }
}
