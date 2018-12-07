package com.coexplore.api.service;

import com.coexplore.api.service.dto.SettingBannerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SettingBanner.
 */
public interface SettingBannerService {

    /**
     * Save a settingBanner.
     *
     * @param settingBannerDTO the entity to save
     * @return the persisted entity
     */
    SettingBannerDTO save(SettingBannerDTO settingBannerDTO);

    /**
     * Get all the settingBanners.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SettingBannerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" settingBanner.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SettingBannerDTO> findOne(Long id);

    /**
     * Delete the "id" settingBanner.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
