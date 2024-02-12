package com.photos.backup.entity;

import com.photos.backup.constants.DatabaseConstants;
import com.photos.backup.pojo.GeoLocation;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Entity(name = DatabaseConstants.PHOTOS_DATABASE_NAME)
public class Photo {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String thumbnailPath;

    @Column(nullable = false)
    private String originalName;

    private long size;

    @Embedded
    private GeoLocation location;


    private String caption;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Date uploadDate;

    private Date modificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Transient
    private String downloadLink;

    @Transient
    private String thumbnailLink;

}
