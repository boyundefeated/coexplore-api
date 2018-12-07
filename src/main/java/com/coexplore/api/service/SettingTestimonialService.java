package com.coexplore.api.service;

import com.coexplore.api.service.dto.SettingTestimonialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SettingTestimonial.
 */
public interface SettingTestimonialService {

    /**
     * Save a settingTestimonial.
     *
     * @param settingTestimonialDTO the entity to save
     * @return the persisted entity
     */
    SettingTestimonialDTO save(SettingTestimonialDTO settingTestimonialDTO);

    /**
     * Get all the settingTestimonials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SettingTestimonialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" settingTestimonial.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SettingTestimonialDTO> findOne(Long id);

    /**
     * Delete the "id" settingTestimonial.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
