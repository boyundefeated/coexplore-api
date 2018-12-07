package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.OccupiedRoomService;
import com.coexplore.api.service.dto.OccupiedRoomDTO;
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
 * REST controller for managing OccupiedRoom.
 */
@RestController
@RequestMapping("/api")
public class OccupiedRoomResource {

    private final Logger log = LoggerFactory.getLogger(OccupiedRoomResource.class);

    private static final String ENTITY_NAME = "occupiedRoom";

    private final OccupiedRoomService occupiedRoomService;

    public OccupiedRoomResource(OccupiedRoomService occupiedRoomService) {
        this.occupiedRoomService = occupiedRoomService;
    }

    /**
     * POST  /occupied-rooms : Create a new occupiedRoom.
     *
     * @param occupiedRoomDTO the occupiedRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new occupiedRoomDTO, or with status 400 (Bad Request) if the occupiedRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/occupied-rooms")
    @Timed
    public ResponseEntity<OccupiedRoomDTO> createOccupiedRoom(@RequestBody OccupiedRoomDTO occupiedRoomDTO) throws URISyntaxException {
        log.debug("REST request to save OccupiedRoom : {}", occupiedRoomDTO);
        if (occupiedRoomDTO.getId() != null) {
            throw new RuntimeException("A new occupiedRoom cannot already have an ID");
        }
        OccupiedRoomDTO result = occupiedRoomService.save(occupiedRoomDTO);
        return ResponseEntity.created(new URI("/api/occupied-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /occupied-rooms : Updates an existing occupiedRoom.
     *
     * @param occupiedRoomDTO the occupiedRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated occupiedRoomDTO,
     * or with status 400 (Bad Request) if the occupiedRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the occupiedRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/occupied-rooms")
    @Timed
    public ResponseEntity<OccupiedRoomDTO> updateOccupiedRoom(@RequestBody OccupiedRoomDTO occupiedRoomDTO) throws URISyntaxException {
        log.debug("REST request to update OccupiedRoom : {}", occupiedRoomDTO);
        if (occupiedRoomDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        OccupiedRoomDTO result = occupiedRoomService.save(occupiedRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, occupiedRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /occupied-rooms : get all the occupiedRooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of occupiedRooms in body
     */
    @GetMapping("/occupied-rooms")
    @Timed
    public ResponseEntity<List<OccupiedRoomDTO>> getAllOccupiedRooms(Pageable pageable) {
        log.debug("REST request to get a page of OccupiedRooms");
        Page<OccupiedRoomDTO> page = occupiedRoomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/occupied-rooms");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /occupied-rooms/:id : get the "id" occupiedRoom.
     *
     * @param id the id of the occupiedRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the occupiedRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/occupied-rooms/{id}")
    @Timed
    public ResponseEntity<OccupiedRoomDTO> getOccupiedRoom(@PathVariable Long id) {
        log.debug("REST request to get OccupiedRoom : {}", id);
        Optional<OccupiedRoomDTO> occupiedRoomDTO = occupiedRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(occupiedRoomDTO);
    }

    /**
     * DELETE  /occupied-rooms/:id : delete the "id" occupiedRoom.
     *
     * @param id the id of the occupiedRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/occupied-rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteOccupiedRoom(@PathVariable Long id) {
        log.debug("REST request to delete OccupiedRoom : {}", id);
        occupiedRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
