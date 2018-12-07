package com.coexplore.api.service.impl;

import com.coexplore.api.service.WardService;
import com.coexplore.api.domain.Ward;
import com.coexplore.api.repository.WardRepository;
import com.coexplore.api.service.dto.WardDTO;
import com.coexplore.api.service.mapper.WardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ward.
 */
@Service
@Transactional
public class WardServiceImpl implements WardService {

    private final Logger log = LoggerFactory.getLogger(WardServiceImpl.class);

    private final WardRepository wardRepository;

    private final WardMapper wardMapper;

    public WardServiceImpl(WardRepository wardRepository, WardMapper wardMapper) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
    }

    /**
     * Save a ward.
     *
     * @param wardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WardDTO save(WardDTO wardDTO) {
        log.debug("Request to save Ward : {}", wardDTO);

        Ward ward = wardMapper.toEntity(wardDTO);
        ward = wardRepository.save(ward);
        return wardMapper.toDto(ward);
    }

    /**
     * Get all the wards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wards");
        return wardRepository.findAll(pageable)
            .map(wardMapper::toDto);
    }


    /**
     * Get one ward by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WardDTO> findOne(Long id) {
        log.debug("Request to get Ward : {}", id);
        return wardRepository.findById(id)
            .map(wardMapper::toDto);
    }

    /**
     * Delete the ward by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ward : {}", id);
        wardRepository.deleteById(id);
    }
}
