package com.coexplore.api.service.impl;

import com.coexplore.api.service.SpaceService;
import com.coexplore.api.domain.Space;
import com.coexplore.api.repository.SpaceRepository;
import com.coexplore.api.service.dto.SpaceDTO;
import com.coexplore.api.service.mapper.SpaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Space.
 */
@Service
@Transactional
public class SpaceServiceImpl implements SpaceService {

    private final Logger log = LoggerFactory.getLogger(SpaceServiceImpl.class);

    private final SpaceRepository spaceRepository;

    private final SpaceMapper spaceMapper;

    public SpaceServiceImpl(SpaceRepository spaceRepository, SpaceMapper spaceMapper) {
        this.spaceRepository = spaceRepository;
        this.spaceMapper = spaceMapper;
    }

    /**
     * Save a space.
     *
     * @param spaceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SpaceDTO save(SpaceDTO spaceDTO) {
        log.debug("Request to save Space : {}", spaceDTO);

        Space space = spaceMapper.toEntity(spaceDTO);
        space = spaceRepository.save(space);
        return spaceMapper.toDto(space);
    }

    /**
     * Get all the spaces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Spaces");
        return spaceRepository.findAll(pageable)
            .map(spaceMapper::toDto);
    }


    /**
     * Get one space by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpaceDTO> findOne(Long id) {
        log.debug("Request to get Space : {}", id);
        return spaceRepository.findById(id)
            .map(spaceMapper::toDto);
    }

    /**
     * Delete the space by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Space : {}", id);
        spaceRepository.deleteById(id);
    }
}
