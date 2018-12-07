package com.coexplore.api.service.impl;

import com.coexplore.api.service.SettingTestimonialService;
import com.coexplore.api.domain.SettingTestimonial;
import com.coexplore.api.repository.SettingTestimonialRepository;
import com.coexplore.api.service.dto.SettingTestimonialDTO;
import com.coexplore.api.service.mapper.SettingTestimonialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SettingTestimonial.
 */
@Service
@Transactional
public class SettingTestimonialServiceImpl implements SettingTestimonialService {

    private final Logger log = LoggerFactory.getLogger(SettingTestimonialServiceImpl.class);

    private final SettingTestimonialRepository settingTestimonialRepository;

    private final SettingTestimonialMapper settingTestimonialMapper;

    public SettingTestimonialServiceImpl(SettingTestimonialRepository settingTestimonialRepository, SettingTestimonialMapper settingTestimonialMapper) {
        this.settingTestimonialRepository = settingTestimonialRepository;
        this.settingTestimonialMapper = settingTestimonialMapper;
    }

    /**
     * Save a settingTestimonial.
     *
     * @param settingTestimonialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SettingTestimonialDTO save(SettingTestimonialDTO settingTestimonialDTO) {
        log.debug("Request to save SettingTestimonial : {}", settingTestimonialDTO);

        SettingTestimonial settingTestimonial = settingTestimonialMapper.toEntity(settingTestimonialDTO);
        settingTestimonial = settingTestimonialRepository.save(settingTestimonial);
        return settingTestimonialMapper.toDto(settingTestimonial);
    }

    /**
     * Get all the settingTestimonials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SettingTestimonialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SettingTestimonials");
        return settingTestimonialRepository.findAll(pageable)
            .map(settingTestimonialMapper::toDto);
    }


    /**
     * Get one settingTestimonial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SettingTestimonialDTO> findOne(Long id) {
        log.debug("Request to get SettingTestimonial : {}", id);
        return settingTestimonialRepository.findById(id)
            .map(settingTestimonialMapper::toDto);
    }

    /**
     * Delete the settingTestimonial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SettingTestimonial : {}", id);
        settingTestimonialRepository.deleteById(id);
    }
}
