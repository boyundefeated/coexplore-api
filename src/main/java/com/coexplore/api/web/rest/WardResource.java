package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.WardService;
import com.coexplore.api.service.dto.WardDTO;
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
 * REST controller for managing Ward.
 */
@RestController
@RequestMapping("/api")
public class WardResource {

    private final Logger log = LoggerFactory.getLogger(WardResource.class);

    private static final String ENTITY_NAME = "ward";

    private final WardService wardService;

    public WardResource(WardService wardService) {
        this.wardService = wardService;
    }

    /**
     * POST  /wards : Create a new ward.
     *
     * @param wardDTO the wardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wardDTO, or with status 400 (Bad Request) if the ward has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wards")
    @Timed
    public ResponseEntity<WardDTO> createWard(@RequestBody WardDTO wardDTO) throws URISyntaxException {
        log.debug("REST request to save Ward : {}", wardDTO);
        if (wardDTO.getId() != null) {
            throw new RuntimeException("A new ward cannot already have an ID");
        }
        WardDTO result = wardService.save(wardDTO);
        return ResponseEntity.created(new URI("/api/wards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wards : Updates an existing ward.
     *
     * @param wardDTO the wardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wardDTO,
     * or with status 400 (Bad Request) if the wardDTO is not valid,
     * or with status 500 (Internal Server Error) if the wardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wards")
    @Timed
    public ResponseEntity<WardDTO> updateWard(@RequestBody WardDTO wardDTO) throws URISyntaxException {
        log.debug("REST request to update Ward : {}", wardDTO);
        if (wardDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        WardDTO result = wardService.save(wardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wards : get all the wards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wards in body
     */
    @GetMapping("/wards")
    @Timed
    public ResponseEntity<List<WardDTO>> getAllWards(Pageable pageable) {
        log.debug("REST request to get a page of Wards");
        Page<WardDTO> page = wardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wards");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /wards/:id : get the "id" ward.
     *
     * @param id the id of the wardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wards/{id}")
    @Timed
    public ResponseEntity<WardDTO> getWard(@PathVariable Long id) {
        log.debug("REST request to get Ward : {}", id);
        Optional<WardDTO> wardDTO = wardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wardDTO);
    }

    /**
     * DELETE  /wards/:id : delete the "id" ward.
     *
     * @param id the id of the wardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wards/{id}")
    @Timed
    public ResponseEntity<Void> deleteWard(@PathVariable Long id) {
        log.debug("REST request to delete Ward : {}", id);
        wardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
