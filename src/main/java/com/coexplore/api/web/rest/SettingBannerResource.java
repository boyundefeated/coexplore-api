package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.SettingBannerService;
import com.coexplore.api.service.dto.SettingBannerDTO;
import com.coexplore.api.web.rest.util.HeaderUtil;
import com.coexplore.api.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing SettingBanner.
 */
@RestController
@RequestMapping("/api")
public class SettingBannerResource {

    private final Logger log = LoggerFactory.getLogger(SettingBannerResource.class);

    private static final String ENTITY_NAME = "settingBanner";

    private final SettingBannerService settingBannerService;

    public SettingBannerResource(SettingBannerService settingBannerService) {
        this.settingBannerService = settingBannerService;
    }

    /**
     * POST  /setting-banners : Create a new settingBanner.
     *
     * @param settingBannerDTO the settingBannerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settingBannerDTO, or with status 400 (Bad Request) if the settingBanner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/setting-banners")
    @Timed
    public ResponseEntity<SettingBannerDTO> createSettingBanner(@RequestBody SettingBannerDTO settingBannerDTO) throws URISyntaxException {
        log.debug("REST request to save SettingBanner : {}", settingBannerDTO);
        if (settingBannerDTO.getId() != null) {
            throw new RuntimeException("A new settingBanner cannot already have an ID");
        }
        SettingBannerDTO result = settingBannerService.save(settingBannerDTO);
        return ResponseEntity.created(new URI("/api/setting-banners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setting-banners : Updates an existing settingBanner.
     *
     * @param settingBannerDTO the settingBannerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settingBannerDTO,
     * or with status 400 (Bad Request) if the settingBannerDTO is not valid,
     * or with status 500 (Internal Server Error) if the settingBannerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/setting-banners")
    @Timed
    public ResponseEntity<SettingBannerDTO> updateSettingBanner(@RequestBody SettingBannerDTO settingBannerDTO) throws URISyntaxException {
        log.debug("REST request to update SettingBanner : {}", settingBannerDTO);
        if (settingBannerDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        SettingBannerDTO result = settingBannerService.save(settingBannerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settingBannerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setting-banners : get all the settingBanners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of settingBanners in body
     */
    @GetMapping("/setting-banners")
    @Timed
    public ResponseEntity<List<SettingBannerDTO>> getAllSettingBanners(Pageable pageable) {
        log.debug("REST request to get a page of SettingBanners");
        Page<SettingBannerDTO> page = settingBannerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setting-banners");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /setting-banners/:id : get the "id" settingBanner.
     *
     * @param id the id of the settingBannerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settingBannerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/setting-banners/{id}")
    @Timed
    public ResponseEntity<SettingBannerDTO> getSettingBanner(@PathVariable Long id) {
        log.debug("REST request to get SettingBanner : {}", id);
        Optional<SettingBannerDTO> settingBannerDTO = settingBannerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingBannerDTO);
    }

    /**
     * DELETE  /setting-banners/:id : delete the "id" settingBanner.
     *
     * @param id the id of the settingBannerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/setting-banners/{id}")
    @Timed
    public ResponseEntity<Void> deleteSettingBanner(@PathVariable Long id) {
        log.debug("REST request to delete SettingBanner : {}", id);
        settingBannerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
