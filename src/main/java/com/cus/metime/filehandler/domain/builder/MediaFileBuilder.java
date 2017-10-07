package com.cus.metime.filehandler.domain.builder;

import com.cus.metime.filehandler.domain.MediaFile;
import com.cus.metime.filehandler.domain.embedable.CreationalDate;
import com.cus.metime.filehandler.domain.embedable.FileSpecification;

public class MediaFileBuilder {
    private String segment;
    private String uuid;
    private CreationalDate creationalDate;
    private FileSpecification fileSpecification;

    public MediaFileBuilder setSegment(String segment) {
        this.segment = segment;
        return this;
    }

    public MediaFileBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public MediaFileBuilder setCreationalDate(CreationalDate creationalDate) {
        this.creationalDate = creationalDate;
        return this;
    }

    public MediaFileBuilder setFileSpecification(FileSpecification fileSpecification) {
        this.fileSpecification = fileSpecification;
        return this;
    }

    public MediaFile createMediaFile() {
        return new MediaFile(segment, uuid, creationalDate, fileSpecification);
    }
}
