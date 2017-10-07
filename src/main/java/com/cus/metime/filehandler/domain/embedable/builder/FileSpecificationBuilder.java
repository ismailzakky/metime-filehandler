package com.cus.metime.filehandler.domain.embedable.builder;

import com.cus.metime.filehandler.domain.embedable.FileSpecification;

public class FileSpecificationBuilder {
    private String fileName;
    private String mimeType;
    private Float originalSize;
    private String extension;

    public FileSpecificationBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileSpecificationBuilder setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public FileSpecificationBuilder setOriginalSize(Float originalSize) {
        this.originalSize = originalSize;
        return this;
    }

    public FileSpecificationBuilder setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    public FileSpecification createFileSpecification() {
        return new FileSpecification(fileName, mimeType, originalSize, extension);
    }
}
