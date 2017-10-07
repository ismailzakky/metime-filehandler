package com.cus.metime.filehandler.domain.embedable;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by C-US on 9/29/2017.
 */
@Embeddable
public class CreationalDate implements Serializable{

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

    public CreationalDate() {
    }

    public CreationalDate(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreationalDate)) return false;

        CreationalDate that = (CreationalDate) o;

        if (getCreatedAt() != null ? !getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getModifiedAt() != null ? !getModifiedAt().equals(that.getModifiedAt()) : that.getModifiedAt() != null)
            return false;
        return getModifiedBy() != null ? getModifiedBy().equals(that.getModifiedBy()) : that.getModifiedBy() == null;
    }

    @Override
    public int hashCode() {
        int result = getCreatedAt() != null ? getCreatedAt().hashCode() : 0;
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getModifiedAt() != null ? getModifiedAt().hashCode() : 0);
        result = 31 * result + (getModifiedBy() != null ? getModifiedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreationalDate{" +
            "createdAt=" + createdAt +
            ", createdBy='" + createdBy + '\'' +
            ", modifiedAt=" + modifiedAt +
            ", modifiedBy='" + modifiedBy + '\'' +
            '}';
    }
}
