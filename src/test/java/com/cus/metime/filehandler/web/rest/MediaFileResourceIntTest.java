package com.cus.metime.filehandler.web.rest;

import com.cus.metime.filehandler.FilehandlerApp;

import com.cus.metime.filehandler.config.SecurityBeanOverrideConfiguration;

import com.cus.metime.filehandler.domain.MediaFile;
import com.cus.metime.filehandler.domain.builder.MediaFileBuilder;
import com.cus.metime.filehandler.repository.MediaFileRepository;
import com.cus.metime.filehandler.service.MediaFileService;
import com.cus.metime.filehandler.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MediaFileResource REST controller.
 *
 * @see MediaFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FilehandlerApp.class, SecurityBeanOverrideConfiguration.class})
public class MediaFileResourceIntTest {

    private static final String DEFAULT_SEGMENT = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private MediaFileService mediaFileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaFileMockMvc;

    private MediaFile mediaFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaFileResource mediaFileResource = new MediaFileResource(mediaFileService);
        this.restMediaFileMockMvc = MockMvcBuilders.standaloneSetup(mediaFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaFile createEntity(EntityManager em) {
        MediaFile mediaFile = new MediaFileBuilder().createMediaFile()
            .segment(DEFAULT_SEGMENT)
            .uuid(DEFAULT_UUID);
        return mediaFile;
    }

    @Before
    public void initTest() {
        mediaFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaFile() throws Exception {
        int databaseSizeBeforeCreate = mediaFileRepository.findAll().size();

        // Create the MediaFile
        restMediaFileMockMvc.perform(post("/api/media-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaFile)))
            .andExpect(status().isCreated());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeCreate + 1);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getSegment()).isEqualTo(DEFAULT_SEGMENT);
        assertThat(testMediaFile.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createMediaFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaFileRepository.findAll().size();

        // Create the MediaFile with an existing ID
        mediaFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaFileMockMvc.perform(post("/api/media-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaFile)))
            .andExpect(status().isBadRequest());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMediaFiles() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        // Get all the mediaFileList
        restMediaFileMockMvc.perform(get("/api/media-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].segment").value(hasItem(DEFAULT_SEGMENT.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }

    @Test
    @Transactional
    public void getMediaFile() throws Exception {
        // Initialize the database
        mediaFileRepository.saveAndFlush(mediaFile);

        // Get the mediaFile
        restMediaFileMockMvc.perform(get("/api/media-files/{id}", mediaFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mediaFile.getId().intValue()))
            .andExpect(jsonPath("$.segment").value(DEFAULT_SEGMENT.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaFile() throws Exception {
        // Get the mediaFile
        restMediaFileMockMvc.perform(get("/api/media-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaFile() throws Exception {
        // Initialize the database
        mediaFileService.save(mediaFile);

        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();

        // Update the mediaFile
        MediaFile updatedMediaFile = mediaFileRepository.findOne(mediaFile.getId());
        updatedMediaFile
            .segment(UPDATED_SEGMENT)
            .uuid(UPDATED_UUID);

        restMediaFileMockMvc.perform(put("/api/media-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMediaFile)))
            .andExpect(status().isOk());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate);
        MediaFile testMediaFile = mediaFileList.get(mediaFileList.size() - 1);
        assertThat(testMediaFile.getSegment()).isEqualTo(UPDATED_SEGMENT);
        assertThat(testMediaFile.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaFile() throws Exception {
        int databaseSizeBeforeUpdate = mediaFileRepository.findAll().size();

        // Create the MediaFile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaFileMockMvc.perform(put("/api/media-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaFile)))
            .andExpect(status().isCreated());

        // Validate the MediaFile in the database
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMediaFile() throws Exception {
        // Initialize the database
        mediaFileService.save(mediaFile);

        int databaseSizeBeforeDelete = mediaFileRepository.findAll().size();

        // Get the mediaFile
        restMediaFileMockMvc.perform(delete("/api/media-files/{id}", mediaFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MediaFile> mediaFileList = mediaFileRepository.findAll();
        assertThat(mediaFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaFile.class);
        MediaFile mediaFile1 = new MediaFileBuilder().createMediaFile();
        mediaFile1.setId(1L);
        MediaFile mediaFile2 = new MediaFileBuilder().createMediaFile();
        mediaFile2.setId(mediaFile1.getId());
        assertThat(mediaFile1).isEqualTo(mediaFile2);
        mediaFile2.setId(2L);
        assertThat(mediaFile1).isNotEqualTo(mediaFile2);
        mediaFile1.setId(null);
        assertThat(mediaFile1).isNotEqualTo(mediaFile2);
    }
}
