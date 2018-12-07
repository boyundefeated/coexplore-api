package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.SettingContentService;
import com.coexplore.api.service.dto.SettingContentDTO;
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
 * REST controller for managing SettingContent.
 */
@RestController
@RequestMapping("/api")
public class SettingContentResource {

    private final Logger log = LoggerFactory.getLogger(SettingContentResource.class);

    private static final String ENTITY_NAME = "settingContent";

    private final SettingContentService settingContentService;

    public SettingContentResource(SettingContentService settingContentService) {
        this.settingContentService = settingContentService;
    }

    /**
     * POST  /setting-contents : Create a new settingContent.
     *
     * @param settingContentDTO the settingContentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settingContentDTO, or with status 400 (Bad Request) if the settingContent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/setting-contents")
    @Timed
    public ResponseEntity<SettingContentDTO> createSettingContent(@RequestBody SettingContentDTO settingContentDTO) throws URISyntaxException {
        log.debug("REST request to save SettingContent : {}", settingContentDTO);
        if (settingContentDTO.getId() != null) {
            throw new RuntimeException("A new settingContent cannot already have an ID");
        }
        SettingContentDTO result = settingContentService.save(settingContentDTO);
        return ResponseEntity.created(new URI("/api/setting-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setting-contents : Updates an existing settingContent.
     *
     * @param settingContentDTO the settingContentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settingContentDTO,
     * or with status 400 (Bad Request) if the settingContentDTO is not valid,
     * or with status 500 (Internal Server Error) if the settingContentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/setting-contents")
    @Timed
    public ResponseEntity<SettingContentDTO> updateSettingContent(@RequestBody SettingContentDTO settingContentDTO) throws URISyntaxException {
        log.debug("REST request to update SettingContent : {}", settingContentDTO);
        if (settingContentDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        SettingContentDTO result = settingContentService.save(settingContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settingContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setting-contents : get all the settingContents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of settingContents in body
     */
    @GetMapping("/setting-contents")
    @Timed
    public ResponseEntity<List<SettingContentDTO>> getAllSettingContents(Pageable pageable) {
        log.debug("REST request to get a page of SettingContents");
        Page<SettingContentDTO> page = settingContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setting-contents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /setting-contents/:id : get the "id" settingContent.
     *
     * @param id the id of the settingContentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settingContentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/setting-contents/{id}")
    @Timed
    public ResponseEntity<SettingContentDTO> getSettingContent(@PathVariable Long id) {
        log.debug("REST request to get SettingContent : {}", id);
        Optional<SettingContentDTO> settingContentDTO = settingContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingContentDTO);
    }

    /**
     * DELETE  /setting-contents/:id : delete the "id" settingContent.
     *
     * @param id the id of the settingContentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/setting-contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteSettingContent(@PathVariable Long id) {
        log.debug("REST request to delete SettingContent : {}", id);
        settingContentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
