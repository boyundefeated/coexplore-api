package com.coexplore.api.service;

import com.coexplore.api.service.dto.SettingContentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SettingContent.
 */
public interface SettingContentService {

    /**
     * Save a settingContent.
     *
     * @param settingContentDTO the entity to save
     * @return the persisted entity
     */
    SettingContentDTO save(SettingContentDTO settingContentDTO);

    /**
     * Get all the settingContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SettingContentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" settingContent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SettingContentDTO> findOne(Long id);

    /**
     * Delete the "id" settingContent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
