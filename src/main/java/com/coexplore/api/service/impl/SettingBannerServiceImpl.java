package com.coexplore.api.service.impl;

import com.coexplore.api.service.SettingBannerService;
import com.coexplore.api.domain.SettingBanner;
import com.coexplore.api.repository.SettingBannerRepository;
import com.coexplore.api.service.dto.SettingBannerDTO;
import com.coexplore.api.service.mapper.SettingBannerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SettingBanner.
 */
@Service
@Transactional
public class SettingBannerServiceImpl implements SettingBannerService {

    private final Logger log = LoggerFactory.getLogger(SettingBannerServiceImpl.class);

    private final SettingBannerRepository settingBannerRepository;

    private final SettingBannerMapper settingBannerMapper;

    public SettingBannerServiceImpl(SettingBannerRepository settingBannerRepository, SettingBannerMapper settingBannerMapper) {
        this.settingBannerRepository = settingBannerRepository;
        this.settingBannerMapper = settingBannerMapper;
    }

    /**
     * Save a settingBanner.
     *
     * @param settingBannerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SettingBannerDTO save(SettingBannerDTO settingBannerDTO) {
        log.debug("Request to save SettingBanner : {}", settingBannerDTO);

        SettingBanner settingBanner = settingBannerMapper.toEntity(settingBannerDTO);
        settingBanner = settingBannerRepository.save(settingBanner);
        return settingBannerMapper.toDto(settingBanner);
    }

    /**
     * Get all the settingBanners.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SettingBannerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SettingBanners");
        return settingBannerRepository.findAll(pageable)
            .map(settingBannerMapper::toDto);
    }


    /**
     * Get one settingBanner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SettingBannerDTO> findOne(Long id) {
        log.debug("Request to get SettingBanner : {}", id);
        return settingBannerRepository.findById(id)
            .map(settingBannerMapper::toDto);
    }

    /**
     * Delete the settingBanner by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SettingBanner : {}", id);
        settingBannerRepository.deleteById(id);
    }
}
