package com.coexplore.api.service.impl;

import com.coexplore.api.service.SettingContentService;
import com.coexplore.api.domain.SettingContent;
import com.coexplore.api.repository.SettingContentRepository;
import com.coexplore.api.service.dto.SettingContentDTO;
import com.coexplore.api.service.mapper.SettingContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SettingContent.
 */
@Service
@Transactional
public class SettingContentServiceImpl implements SettingContentService {

    private final Logger log = LoggerFactory.getLogger(SettingContentServiceImpl.class);

    private final SettingContentRepository settingContentRepository;

    private final SettingContentMapper settingContentMapper;

    public SettingContentServiceImpl(SettingContentRepository settingContentRepository, SettingContentMapper settingContentMapper) {
        this.settingContentRepository = settingContentRepository;
        this.settingContentMapper = settingContentMapper;
    }

    /**
     * Save a settingContent.
     *
     * @param settingContentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SettingContentDTO save(SettingContentDTO settingContentDTO) {
        log.debug("Request to save SettingContent : {}", settingContentDTO);

        SettingContent settingContent = settingContentMapper.toEntity(settingContentDTO);
        settingContent = settingContentRepository.save(settingContent);
        return settingContentMapper.toDto(settingContent);
    }

    /**
     * Get all the settingContents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SettingContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SettingContents");
        return settingContentRepository.findAll(pageable)
            .map(settingContentMapper::toDto);
    }


    /**
     * Get one settingContent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SettingContentDTO> findOne(Long id) {
        log.debug("Request to get SettingContent : {}", id);
        return settingContentRepository.findById(id)
            .map(settingContentMapper::toDto);
    }

    /**
     * Delete the settingContent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SettingContent : {}", id);
        settingContentRepository.deleteById(id);
    }
}
