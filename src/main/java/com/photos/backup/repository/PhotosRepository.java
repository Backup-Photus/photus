package com.photos.backup.repository;

import com.photos.backup.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotosRepository extends JpaRepository<Photo, UUID> {
    Page<Photo> findAllByUserId(UUID userId, Pageable pageable);
}
