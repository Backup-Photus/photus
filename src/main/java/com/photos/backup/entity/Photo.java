package com.photos.backup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photos.backup.constants.DatabaseConstants;
import com.photos.backup.pojo.GeoLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = DatabaseConstants.PHOTOS_DATABASE_NAME)
public class Photo {

    @Id
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

    @Transient
    private String downloadLink;

    @Transient
    private String thumbnailLink;

    private Photo(Builder builder) {
        id = builder.id;
        path = builder.path;
        originalName = builder.originalName;
        size = builder.size;
        location = builder.location;
        caption = builder.caption;
        creationDate = builder.creationDate;
        uploadDate = builder.uploadDate;
        modificationDate = builder.modificationDate;
        user = builder.user;
    }
    public static  class Builder {
        private UUID id;
        private String path;
        private String originalName;
        private long size;
        private GeoLocation location;
        private String caption;
        private Date creationDate;
        private Date uploadDate;
        private Date modificationDate;
        private User user;

        public Builder() {
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder id(UUID val){
            id=val;
            return  this;
        }

        public Builder originalName(String val) {
            originalName = val;
            return this;
        }

        public Builder size(long val) {
            size = val;
            return this;
        }

        public Builder location(GeoLocation val) {
            location = val;
            return this;
        }

        public Builder caption(String val) {
            caption = val;
            return this;
        }

        public Builder modificationDate(Date val) {
            modificationDate = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Photo build() {
            this.creationDate = new Date(System.currentTimeMillis());
            this.uploadDate = this.creationDate;
            this.id = UUID.randomUUID();
            return new Photo(this);
        }
    }
}
