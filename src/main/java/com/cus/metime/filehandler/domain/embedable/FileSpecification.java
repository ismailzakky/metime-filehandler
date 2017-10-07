package com.cus.metime.filehandler.domain.embedable;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by C-US on 9/29/2017.
 */
@Embeddable
public class FileSpecification implements Serializable{

    private String fileName;
    private String mimeType;
    private Float originalSize;
    private String extension;

    public FileSpecification() {
    }

    public FileSpecification(String fileName, String mimeType, Float originalSize, String extension) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.originalSize = originalSize;
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Float getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(Float originalSize) {
        this.originalSize = originalSize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof FileSpecification)) return false;

        FileSpecification that = (FileSpecification) o;

        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
            return false;
        if (getMimeType() != null ? !getMimeType().equals(that.getMimeType()) : that.getMimeType() != null)
            return false;
        if (getOriginalSize() != null ? !getOriginalSize().equals(that.getOriginalSize()) : that.getOriginalSize() != null)
            return false;
        return getExtension() != null ? getExtension().equals(that.getExtension()) : that.getExtension() == null;
    }

    @Override
    public int hashCode() {
        int result = getFileName() != null ? getFileName().hashCode() : 0;
        result = 31 * result + (getMimeType() != null ? getMimeType().hashCode() : 0);
        result = 31 * result + (getOriginalSize() != null ? getOriginalSize().hashCode() : 0);
        result = 31 * result + (getExtension() != null ? getExtension().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileSpecification{" +
            "fileName='" + fileName + '\'' +
            ", mimeType='" + mimeType + '\'' +
            ", originalSize=" + originalSize +
            ", extension='" + extension + '\'' +
            '}';
    }
}
