package com.cus.metime.filehandler.service;

import com.cus.metime.filehandler.domain.MediaFile;
import java.util.List;

/**
 * Service Interface for managing MediaFile.
 */
public interface MediaFileService {

    /**
     * Save a mediaFile.
     *
     * @param mediaFile the entity to save
     * @return the persisted entity
     */
    MediaFile save(MediaFile mediaFile);

    /**
     *  Get all the mediaFiles.
     *
     *  @return the list of entities
     */
    List<MediaFile> findAll();

    /**
     *  Get the "id" mediaFile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MediaFile findOne(Long id);

    /**
     *  Delete the "id" mediaFile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
