package com.photos.backup.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photo_id")
    private UUID photoId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "source_file")
    @JsonAlias({"SourceFile"})
    private String sourceFile;

    @Column(name = "file_name")
    @JsonAlias({"FileName"})
    private String fileName;

    @Column(name = "directory")
    @JsonAlias({"Directory"})
    private String directory;

    @Column(name = "file_size")
    @JsonAlias({"FileSize"})
    private String fileSize;

    @Column(name = "file_modify_date")
    @JsonAlias({"FileModifyDate"})
    private String fileModifyDate;

    @Column(name = "file_access_date")
    @JsonAlias({"FileAccessDate"})
    private String fileAccessDate;

    @Column(name = "file_type")
    @JsonAlias({"FileType"})
    private String fileType;

    @Column(name = "mime_type")
    @JsonAlias({"MIMEType"})
    private String mIMEType;

    @Column(name = "make")
    @JsonAlias({"Make"})
    private String make;

    @Column(name = "model")
    @JsonAlias({"Model"})
    private String model;

    @Column(name = "x_resolution")
    @JsonAlias({"XResolution"})
    private int xResolution;

    @Column(name = "y_resolution")
    @JsonAlias({"YResolution"})
    private int yResolution;

    @Column(name = "resolution_unit")
    @JsonAlias({"ResolutionUnit"})
    private String resolutionUnit;

    @Column(name = "exposure_time")
    @JsonAlias({"ExposureTime"})
    private String exposureTime;

    @Column(name = "f_number")
    @JsonAlias({"FNumber"})
    private double fNumber;

    @Column(name = "iso")
    @JsonAlias({"ISO"})
    private int iSO;

    @Column(name = "date_time_original")
    @JsonAlias({"DateTimeOriginal"})
    private String dateTimeOriginal;

    @Column(name = "gps_latitude")
    @JsonAlias({"GPSLatitude"})
    private String gPSLatitude;

    @Column(name = "gps_longitude")
    @JsonAlias({"GPSLongitude"})
    private String gPSLongitude;

    @Column(name = "lens_id")
    @JsonAlias({"LensID"})
    private String lensID;
}
