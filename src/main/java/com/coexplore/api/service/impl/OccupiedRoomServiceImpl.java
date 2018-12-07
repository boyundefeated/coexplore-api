package com.coexplore.api.service.impl;

import com.coexplore.api.service.OccupiedRoomService;
import com.coexplore.api.domain.OccupiedRoom;
import com.coexplore.api.repository.OccupiedRoomRepository;
import com.coexplore.api.service.dto.OccupiedRoomDTO;
import com.coexplore.api.service.mapper.OccupiedRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OccupiedRoom.
 */
@Service
@Transactional
public class OccupiedRoomServiceImpl implements OccupiedRoomService {

    private final Logger log = LoggerFactory.getLogger(OccupiedRoomServiceImpl.class);

    private final OccupiedRoomRepository occupiedRoomRepository;

    private final OccupiedRoomMapper occupiedRoomMapper;

    public OccupiedRoomServiceImpl(OccupiedRoomRepository occupiedRoomRepository, OccupiedRoomMapper occupiedRoomMapper) {
        this.occupiedRoomRepository = occupiedRoomRepository;
        this.occupiedRoomMapper = occupiedRoomMapper;
    }

    /**
     * Save a occupiedRoom.
     *
     * @param occupiedRoomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OccupiedRoomDTO save(OccupiedRoomDTO occupiedRoomDTO) {
        log.debug("Request to save OccupiedRoom : {}", occupiedRoomDTO);

        OccupiedRoom occupiedRoom = occupiedRoomMapper.toEntity(occupiedRoomDTO);
        occupiedRoom = occupiedRoomRepository.save(occupiedRoom);
        return occupiedRoomMapper.toDto(occupiedRoom);
    }

    /**
     * Get all the occupiedRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OccupiedRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OccupiedRooms");
        return occupiedRoomRepository.findAll(pageable)
            .map(occupiedRoomMapper::toDto);
    }


    /**
     * Get one occupiedRoom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OccupiedRoomDTO> findOne(Long id) {
        log.debug("Request to get OccupiedRoom : {}", id);
        return occupiedRoomRepository.findById(id)
            .map(occupiedRoomMapper::toDto);
    }

    /**
     * Delete the occupiedRoom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OccupiedRoom : {}", id);
        occupiedRoomRepository.deleteById(id);
    }
}
