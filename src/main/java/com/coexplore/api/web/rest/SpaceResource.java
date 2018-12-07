package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.SpaceService;
import com.coexplore.api.service.dto.SpaceDTO;
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
 * REST controller for managing Space.
 */
@RestController
@RequestMapping("/api")
public class SpaceResource {

    private final Logger log = LoggerFactory.getLogger(SpaceResource.class);

    private static final String ENTITY_NAME = "space";

    private final SpaceService spaceService;

    public SpaceResource(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    /**
     * POST  /spaces : Create a new space.
     *
     * @param spaceDTO the spaceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spaceDTO, or with status 400 (Bad Request) if the space has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/spaces")
    @Timed
    public ResponseEntity<SpaceDTO> createSpace(@RequestBody SpaceDTO spaceDTO) throws URISyntaxException {
        log.debug("REST request to save Space : {}", spaceDTO);
        if (spaceDTO.getId() != null) {
            throw new RuntimeException("A new space cannot already have an ID");
        }
        SpaceDTO result = spaceService.save(spaceDTO);
        return ResponseEntity.created(new URI("/api/spaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spaces : Updates an existing space.
     *
     * @param spaceDTO the spaceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spaceDTO,
     * or with status 400 (Bad Request) if the spaceDTO is not valid,
     * or with status 500 (Internal Server Error) if the spaceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/spaces")
    @Timed
    public ResponseEntity<SpaceDTO> updateSpace(@RequestBody SpaceDTO spaceDTO) throws URISyntaxException {
        log.debug("REST request to update Space : {}", spaceDTO);
        if (spaceDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        SpaceDTO result = spaceService.save(spaceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, spaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spaces : get all the spaces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of spaces in body
     */
    @GetMapping("/spaces")
    @Timed
    public ResponseEntity<List<SpaceDTO>> getAllSpaces(Pageable pageable) {
        log.debug("REST request to get a page of Spaces");
        Page<SpaceDTO> page = spaceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/spaces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /spaces/:id : get the "id" space.
     *
     * @param id the id of the spaceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spaceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/spaces/{id}")
    @Timed
    public ResponseEntity<SpaceDTO> getSpace(@PathVariable Long id) {
        log.debug("REST request to get Space : {}", id);
        Optional<SpaceDTO> spaceDTO = spaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spaceDTO);
    }

    /**
     * DELETE  /spaces/:id : delete the "id" space.
     *
     * @param id the id of the spaceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/spaces/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        log.debug("REST request to delete Space : {}", id);
        spaceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
