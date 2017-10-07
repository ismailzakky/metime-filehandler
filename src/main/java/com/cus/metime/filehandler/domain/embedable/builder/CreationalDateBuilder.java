package com.cus.metime.filehandler.domain.embedable.builder;

import com.cus.metime.filehandler.domain.embedable.CreationalDate;

import java.time.LocalDateTime;

public class CreationalDateBuilder {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

    public CreationalDateBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CreationalDateBuilder setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CreationalDateBuilder setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public CreationalDateBuilder setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public CreationalDate createCreationalDate() {
        return new CreationalDate(createdAt, createdBy, modifiedAt, modifiedBy);
    }
}
