package com.cus.metime.filehandler.domain;


import com.cus.metime.filehandler.domain.embedable.CreationalDate;
import com.cus.metime.filehandler.domain.embedable.FileSpecification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MediaFile.
 */
@Entity
@Table(name = "media_file")
public class MediaFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_segment")
    private String segment;

    @Column(name = "uuid")
    private String uuid;

    @Embedded
    private CreationalDate creationalDate;

    @Embedded
    private FileSpecification fileSpecification;

    public MediaFile() {
    }

    public MediaFile(String segment, String uuid, CreationalDate creationalDate, FileSpecification fileSpecification) {
        this.segment = segment;
        this.uuid = uuid;
        this.creationalDate = creationalDate;
        this.fileSpecification = fileSpecification;
    }

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegment() {
        return segment;
    }

    public MediaFile segment(String segment) {
        this.segment = segment;
        return this;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getUuid() {
        return uuid;
    }

    public MediaFile uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaFile mediaFile = (MediaFile) o;
        if (mediaFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaFile{" +
            "id=" + getId() +
            ", segment='" + getSegment() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
