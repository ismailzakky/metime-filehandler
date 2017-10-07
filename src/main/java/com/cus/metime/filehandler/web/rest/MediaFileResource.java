package com.cus.metime.filehandler.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cus.metime.filehandler.domain.MediaFile;
import com.cus.metime.filehandler.service.MediaFileService;
import com.cus.metime.filehandler.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MediaFile.
 */
@RestController
@RequestMapping("/api")
public class MediaFileResource {

    private final Logger log = LoggerFactory.getLogger(MediaFileResource.class);

    private static final String ENTITY_NAME = "mediaFile";

    private final MediaFileService mediaFileService;

    public MediaFileResource(MediaFileService mediaFileService) {
        this.mediaFileService = mediaFileService;
    }

    /**
     * POST  /media-files : Create a new mediaFile.
     *
     * @param mediaFile the mediaFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaFile, or with status 400 (Bad Request) if the mediaFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media-files")
    @Timed
    public ResponseEntity<MediaFile> createMediaFile(@RequestBody MediaFile mediaFile) throws URISyntaxException {
        log.debug("REST request to save MediaFile : {}", mediaFile);
        if (mediaFile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mediaFile cannot already have an ID")).body(null);
        }
        MediaFile result = mediaFileService.save(mediaFile);
        return ResponseEntity.created(new URI("/api/media-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media-files : Updates an existing mediaFile.
     *
     * @param mediaFile the mediaFile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaFile,
     * or with status 400 (Bad Request) if the mediaFile is not valid,
     * or with status 500 (Internal Server Error) if the mediaFile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media-files")
    @Timed
    public ResponseEntity<MediaFile> updateMediaFile(@RequestBody MediaFile mediaFile) throws URISyntaxException {
        log.debug("REST request to update MediaFile : {}", mediaFile);
        if (mediaFile.getId() == null) {
            return createMediaFile(mediaFile);
        }
        MediaFile result = mediaFileService.save(mediaFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaFile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media-files : get all the mediaFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mediaFiles in body
     */
    @GetMapping("/media-files")
    @Timed
    public List<MediaFile> getAllMediaFiles() {
        log.debug("REST request to get all MediaFiles");
        return mediaFileService.findAll();
        }

    /**
     * GET  /media-files/:id : get the "id" mediaFile.
     *
     * @param id the id of the mediaFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaFile, or with status 404 (Not Found)
     */
    @GetMapping("/media-files/{id}")
    @Timed
    public ResponseEntity<MediaFile> getMediaFile(@PathVariable Long id) {
        log.debug("REST request to get MediaFile : {}", id);
        MediaFile mediaFile = mediaFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mediaFile));
    }

    /**
     * DELETE  /media-files/:id : delete the "id" mediaFile.
     *
     * @param id the id of the mediaFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
        log.debug("REST request to delete MediaFile : {}", id);
        mediaFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
