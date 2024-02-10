package com.photos.backup.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.photos.backup.entity.Photo;

import java.io.IOException;

public class PhotoSerializer extends JsonSerializer<Photo> {
    @Override
    public void serialize(Photo photo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        // Serialize original properties of Photo class
        jsonGenerator.writeStringField("id", photo.getId().toString());
//        jsonGenerator.writeStringField("", );
        // Add additional property (download link)
        jsonGenerator.writeStringField("downloadLink", "https://example.com/download/" + photo.getId());
        jsonGenerator.writeEndObject();
    }
}
