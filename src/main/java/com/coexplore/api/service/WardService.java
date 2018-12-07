package com.coexplore.api.service;

import com.coexplore.api.service.dto.WardDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ward.
 */
public interface WardService {

    /**
     * Save a ward.
     *
     * @param wardDTO the entity to save
     * @return the persisted entity
     */
    WardDTO save(WardDTO wardDTO);

    /**
     * Get all the wards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WardDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ward.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WardDTO> findOne(Long id);

    /**
     * Delete the "id" ward.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
