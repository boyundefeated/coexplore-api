package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.RoomService;
import com.coexplore.api.service.dto.RoomDTO;
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
 * REST controller for managing Room.
 */
@RestController
@RequestMapping("/api")
public class RoomResource {

    private final Logger log = LoggerFactory.getLogger(RoomResource.class);

    private static final String ENTITY_NAME = "room";

    private final RoomService roomService;

    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * POST  /rooms : Create a new room.
     *
     * @param roomDTO the roomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomDTO, or with status 400 (Bad Request) if the room has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rooms")
    @Timed
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) throws URISyntaxException {
        log.debug("REST request to save Room : {}", roomDTO);
        if (roomDTO.getId() != null) {
            throw new RuntimeException("A new room cannot already have an ID");
        }
        RoomDTO result = roomService.save(roomDTO);
        return ResponseEntity.created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rooms : Updates an existing room.
     *
     * @param roomDTO the roomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomDTO,
     * or with status 400 (Bad Request) if the roomDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rooms")
    @Timed
    public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO roomDTO) throws URISyntaxException {
        log.debug("REST request to update Room : {}", roomDTO);
        if (roomDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        RoomDTO result = roomService.save(roomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rooms : get all the rooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rooms in body
     */
    @GetMapping("/rooms")
    @Timed
    public ResponseEntity<List<RoomDTO>> getAllRooms(Pageable pageable) {
        log.debug("REST request to get a page of Rooms");
        Page<RoomDTO> page = roomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rooms");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rooms/:id : get the "id" room.
     *
     * @param id the id of the roomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rooms/{id}")
    @Timed
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        log.debug("REST request to get Room : {}", id);
        Optional<RoomDTO> roomDTO = roomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomDTO);
    }

    /**
     * DELETE  /rooms/:id : delete the "id" room.
     *
     * @param id the id of the roomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.debug("REST request to delete Room : {}", id);
        roomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
