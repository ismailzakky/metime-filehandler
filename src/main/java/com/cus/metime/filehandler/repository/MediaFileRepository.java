package com.cus.metime.filehandler.repository;

import com.cus.metime.filehandler.domain.MediaFile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MediaFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

}
