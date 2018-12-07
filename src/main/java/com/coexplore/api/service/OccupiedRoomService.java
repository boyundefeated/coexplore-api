package com.coexplore.api.service;

import com.coexplore.api.service.dto.OccupiedRoomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OccupiedRoom.
 */
public interface OccupiedRoomService {

    /**
     * Save a occupiedRoom.
     *
     * @param occupiedRoomDTO the entity to save
     * @return the persisted entity
     */
    OccupiedRoomDTO save(OccupiedRoomDTO occupiedRoomDTO);

    /**
     * Get all the occupiedRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OccupiedRoomDTO> findAll(Pageable pageable);


    /**
     * Get the "id" occupiedRoom.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OccupiedRoomDTO> findOne(Long id);

    /**
     * Delete the "id" occupiedRoom.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
