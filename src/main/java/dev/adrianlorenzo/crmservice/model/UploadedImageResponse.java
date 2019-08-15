package dev.adrianlorenzo.crmservice.model;

import lombok.Data;

@Data
public class UploadedImageResponse {
    private final String filename;
    private final String uri;
    private final String contentType;
    private final long size;
}
