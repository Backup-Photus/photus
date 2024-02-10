package com.photos.backup.constants;

import lombok.Getter;

@Getter
public enum ImageType {
    JPEG("image/jpeg", ".jpg"),
    PNG("image/png", ".png"),
    GIF("image/gif", ".gif"),
    BMP("image/bmp", ".bmp"),
    TIFF("image/tiff", ".tiff"),
    WEBP("image/webp", ".webp"),
    SVG("image/svg+xml", ".svg"),
    ICO("image/vnd.microsoft.icon", ".ico"),
    HDR("image/vnd.radiance", ".hdr");

    private final String mimeType;
    private final String fileExtension;

    ImageType(String mimeType, String fileExtension) {
        this.mimeType = mimeType;
        this.fileExtension = fileExtension;
    }

}
