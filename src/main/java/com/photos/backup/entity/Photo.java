package com.photos.backup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photos.backup.constants.DatabaseConstants;
import com.photos.backup.exception.PhotoNotFoundException;
import com.photos.backup.pojo.GeoLocation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = DatabaseConstants.PHOTOS_DATABASE_NAME)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore
    @Column(nullable = false)
    private String path;

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

    @JsonIgnore
    private Date modificationDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public Photo(File photo,User user){
        Date curDate=new Date(System.currentTimeMillis());
        setPath(photo.getPath());
        setOriginalName(photo.getName());
        setUploadDate(curDate);
        setSize(photo.getTotalSpace());
        setCreationDate(curDate);
        setUser(user);
    }
}
