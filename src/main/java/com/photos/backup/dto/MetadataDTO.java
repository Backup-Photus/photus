package com.photos.backup.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record MetadataDTO(

    @JsonAlias("SourceFile")
     String sourceFile,

    @JsonAlias("ExifToolVersion")
     double exifToolVersion,

    @JsonAlias("FileName")
     String fileName,

    @JsonAlias("Directory")
     String directory,

    @JsonAlias("FileSize")
     String fileSize,

    @JsonAlias("FileModifyDate")
     String fileModifyDate,

    @JsonAlias("FileAccessDate")
     String fileAccessDate,

    @JsonAlias("FileInodeChangeDate")
     String fileInodeChangeDate,

    @JsonIgnore
    @JsonAlias("FilePermissions")
     String filePermissions,

    @JsonAlias("FileType")
     String fileType,

    @JsonAlias("FileTypeExtension")
     String fileTypeExtension,

    @JsonAlias("MIMEType")
     String mIMEType,

    @JsonAlias("ExifByteOrder")
     String exifByteOrder,

    @JsonAlias("Make")
     String make,

    @JsonAlias("Model")
     String model,

    @JsonAlias("Orientation")
     String orientation,

    @JsonAlias("XResolution")
     int xResolution,

    @JsonAlias("YResolution")
     int yResolution,

    @JsonAlias("ResolutionUnit")
     String resolutionUnit,

    @JsonAlias("Software")
     String software,

    @JsonAlias("ModifyDate")
     String modifyDate,

    @JsonAlias("YCbCrPositioning")
     String yCbCrPositioning,

    @JsonAlias("ExposureTime")
     String exposureTime,

    @JsonAlias("FNumber")
     double fNumber,

    @JsonAlias("ExposureProgram")
     String exposureProgram,

    @JsonAlias("ISO")
     int iSO,

    @JsonAlias("ExifVersion")
     String exifVersion,

    @JsonAlias("DateTimeOriginal")
     String dateTimeOriginal,

    @JsonAlias("CreateDate")
     String createDate,

    @JsonAlias("OffsetTime")
     String offsetTime,

    @JsonAlias("OffsetTimeOriginal")
     String offsetTimeOriginal,

    @JsonAlias("OffsetTimeDigitized")
     String offsetTimeDigitized,

    @JsonAlias("ComponentsConfiguration")
     String componentsConfiguration,

    @JsonAlias("ShutterSpeedValue")
     String shutterSpeedValue,

    @JsonAlias("ApertureValue")
     double apertureValue,

    @JsonAlias("BrightnessValue")
     double brightnessValue,

    @JsonAlias("ExposureCompensation")
     int exposureCompensation,

    @JsonAlias("MaxApertureValue")
     double maxApertureValue,

    @JsonAlias("SubjectDistance")
     String subjectDistance,

    @JsonAlias("MeteringMode")
     String meteringMode,

    @JsonAlias("Flash")
     String flash,

    @JsonAlias("FocalLength")
     String focalLength,

    @JsonAlias("SubSecTime")
     int subSecTime,

    @JsonAlias("SubSecTimeOriginal")
     int subSecTimeOriginal,

    @JsonAlias("SubSecTimeDigitized")
     int subSecTimeDigitized,

    @JsonAlias("FlashpixVersion")
     String flashpixVersion,

    @JsonAlias("ColorSpace")
     String colorSpace,

    @JsonAlias("ExifImageWidth")
     int exifImageWidth,

    @JsonAlias("ExifImageHeight")
     int exifImageHeight,

    @JsonAlias("InteropIndex")
     String interopIndex,

    @JsonAlias("InteropVersion")
     String interopVersion,

    @JsonAlias("SensingMethod")
     String sensingMethod,

    @JsonAlias("SceneType")
     String sceneType,

    @JsonAlias("CustomRendered")
     String customRendered,

    @JsonAlias("ExposureMode")
     String exposureMode,

    @JsonAlias("WhiteBalance")
     String whiteBalance,

    @JsonAlias("DigitalZoomRatio")
     int digitalZoomRatio,

    @JsonAlias("FocalLengthIn35mmFormat")
     String focalLengthIn35mmFormat,

    @JsonAlias("SceneCaptureType")
     String sceneCaptureType,

    @JsonAlias("Contrast")
     String contrast,

    @JsonAlias("Saturation")
     String saturation,

    @JsonAlias("Sharpness")
     String sharpness,

    @JsonAlias("SubjectDistanceRange")
     String subjectDistanceRange,

    @JsonAlias("LensMake")
     String lensMake,

    @JsonAlias("LensModel")
     String lensModel,

    @JsonAlias("CompositeImage")
     String compositeImage,

    @JsonAlias("GPSLatitudeRef")
     String gPSLatitudeRef,

    @JsonAlias("GPSLongitudeRef")
     String gPSLongitudeRef,

    @JsonAlias("GPSTimeStamp")
     String gPSTimeStamp,

    @JsonAlias("GPSImgDirectionRef")
     String gPSImgDirectionRef,

    @JsonAlias("GPSImgDirection")
     int gPSImgDirection,

    @JsonAlias("GPSDateStamp")
     String gPSDateStamp,

    @JsonAlias("Compression")
     String compression,

    @JsonAlias("ThumbnailOffset")
     int thumbnailOffset,

    @JsonAlias("ThumbnailLength")
     int thumbnailLength,

    @JsonAlias("JFIFVersion")
     double jFIFVersion,

    @JsonAlias("ProfileCMMType")
     String profileCMMType,

    @JsonAlias("ProfileVersion")
     String profileVersion,

    @JsonAlias("ProfileClass")
     String profileClass,

    @JsonAlias("ColorSpaceData")
     String colorSpaceData,

    @JsonAlias("ProfileConnectionSpace")
     String profileConnectionSpace,

    @JsonAlias("ProfileDateTime")
     String profileDateTime,

    @JsonAlias("ProfileFileSignature")
     String profileFileSignature,

    @JsonAlias("PrimaryPlatform")
     String primaryPlatform,

    @JsonAlias("CMMFlags")
     String cMMFlags,

    @JsonAlias("DeviceManufacturer")
     String deviceManufacturer,

    @JsonAlias("DeviceModel")
     String deviceModel,

    @JsonAlias("DeviceAttributes")
     String deviceAttributes,

    @JsonAlias("RenderingIntent")
     String renderingIntent,

    @JsonAlias("ConnectionSpaceIlluminant")
     String connectionSpaceIlluminant,

    @JsonAlias("ProfileCreator")
     String profileCreator,

    @JsonAlias("ProfileID")
     String profileID,

    @JsonAlias("ProfileDescription")
     String profileDescription,

    @JsonAlias("ProfileCopyright")
     String profileCopyright,

    @JsonAlias("MediaWhitePoint")
     String mediaWhitePoint,

    @JsonAlias("MediaBlackPoint")
     String mediaBlackPoint,

    @JsonAlias("RedMatrixColumn")
     String redMatrixColumn,

    @JsonAlias("GreenMatrixColumn")
     String greenMatrixColumn,

    @JsonAlias("BlueMatrixColumn")
     String blueMatrixColumn,

    @JsonAlias("RedTRC")
     String redTRC,

    @JsonAlias("ChromaticAdaptation")
     String chromaticAdaptation,

    @JsonAlias("BlueTRC")
     String blueTRC,

    @JsonAlias("GreenTRC")
     String greenTRC,

    @JsonAlias("XMPToolkit")
     String xMPToolkit,

    @JsonAlias("HasExtendedXMP")
     String hasExtendedXMP,

    @JsonAlias("ImageWidth")
     int imageWidth,

    @JsonAlias("ImageHeight")
     int imageHeight,

    @JsonAlias("EncodingProcess")
     String encodingProcess,

    @JsonAlias("BitsPerSample")
     int bitsPerSample,

    @JsonAlias("ColorComponents")
     int colorComponents,

    @JsonAlias("YCbCrSubSampling")
     String yCbCrSubSampling,

    @JsonAlias("HDRPMakerNote")
     String hDRPMakerNote,

    @JsonAlias("Shot_log_data")
     String shot_log_data,

    @JsonAlias("Aperture")
     double aperture,

    @JsonAlias("ImageSize")
     String imageSize,

    @JsonAlias("Megapixels")
     double megapixels,

    @JsonAlias("ScaleFactor35efl")
     double scaleFactor35efl,

    @JsonAlias("ShutterSpeed")
     String shutterSpeed,

    @JsonAlias("SubSecCreateDate")
     String subSecCreateDate,

    @JsonAlias("SubSecDateTimeOriginal")
     String subSecDateTimeOriginal,

    @JsonAlias("SubSecModifyDate")
     String subSecModifyDate,

    @JsonAlias("ThumbnailImage")
     String thumbnailImage,

    @JsonAlias("GPSDateTime")
     String gPSDateTime,

    @JsonAlias("GPSLatitude")
     String gPSLatitude,

    @JsonAlias("GPSLongitude")
     String gPSLongitude,

    @JsonAlias("CircleOfConfusion")
     String circleOfConfusion,

    @JsonAlias("DOF")
     String dOF,

    @JsonAlias("FOV")
     String fOV,

    @JsonAlias({"FocalLength35efl",})
     String focalLength35efl,

    @JsonAlias("GPSPosition")
     String gPSPosition,

    @JsonAlias("HyperfocalDistance")
     String hyperfocalDistance,

    @JsonAlias("LightValue")
     double lightValue,

    @JsonAlias("LensID")
     String lensID
){}
