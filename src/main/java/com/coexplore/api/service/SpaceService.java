package com.coexplore.api.service;

import com.coexplore.api.service.dto.SpaceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Space.
 */
public interface SpaceService {

    /**
     * Save a space.
     *
     * @param spaceDTO the entity to save
     * @return the persisted entity
     */
    SpaceDTO save(SpaceDTO spaceDTO);

    /**
     * Get all the spaces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SpaceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" space.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SpaceDTO> findOne(Long id);

    /**
     * Delete the "id" space.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
