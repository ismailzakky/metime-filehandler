package com.cus.metime.filehandler.service.impl;

import com.cus.metime.filehandler.service.MediaFileService;
import com.cus.metime.filehandler.domain.MediaFile;
import com.cus.metime.filehandler.repository.MediaFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing MediaFile.
 */
@Service
@Transactional
public class MediaFileServiceImpl implements MediaFileService{

    private final Logger log = LoggerFactory.getLogger(MediaFileServiceImpl.class);

    private final MediaFileRepository mediaFileRepository;

    public MediaFileServiceImpl(MediaFileRepository mediaFileRepository) {
        this.mediaFileRepository = mediaFileRepository;
    }

    /**
     * Save a mediaFile.
     *
     * @param mediaFile the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaFile save(MediaFile mediaFile) {
        log.debug("Request to save MediaFile : {}", mediaFile);
        return mediaFileRepository.save(mediaFile);
    }

    /**
     *  Get all the mediaFiles.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MediaFile> findAll() {
        log.debug("Request to get all MediaFiles");
        return mediaFileRepository.findAll();
    }

    /**
     *  Get one mediaFile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MediaFile findOne(Long id) {
        log.debug("Request to get MediaFile : {}", id);
        return mediaFileRepository.findOne(id);
    }

    /**
     *  Delete the  mediaFile by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MediaFile : {}", id);
        mediaFileRepository.delete(id);
    }
}
