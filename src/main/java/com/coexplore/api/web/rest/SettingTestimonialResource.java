package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.SettingTestimonialService;
import com.coexplore.api.service.dto.SettingTestimonialDTO;
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
 * REST controller for managing SettingTestimonial.
 */
@RestController
@RequestMapping("/api")
public class SettingTestimonialResource {

    private final Logger log = LoggerFactory.getLogger(SettingTestimonialResource.class);

    private static final String ENTITY_NAME = "settingTestimonial";

    private final SettingTestimonialService settingTestimonialService;

    public SettingTestimonialResource(SettingTestimonialService settingTestimonialService) {
        this.settingTestimonialService = settingTestimonialService;
    }

    /**
     * POST  /setting-testimonials : Create a new settingTestimonial.
     *
     * @param settingTestimonialDTO the settingTestimonialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settingTestimonialDTO, or with status 400 (Bad Request) if the settingTestimonial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/setting-testimonials")
    @Timed
    public ResponseEntity<SettingTestimonialDTO> createSettingTestimonial(@RequestBody SettingTestimonialDTO settingTestimonialDTO) throws URISyntaxException {
        log.debug("REST request to save SettingTestimonial : {}", settingTestimonialDTO);
        if (settingTestimonialDTO.getId() != null) {
            throw new RuntimeException("A new settingTestimonial cannot already have an ID");
        }
        SettingTestimonialDTO result = settingTestimonialService.save(settingTestimonialDTO);
        return ResponseEntity.created(new URI("/api/setting-testimonials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /setting-testimonials : Updates an existing settingTestimonial.
     *
     * @param settingTestimonialDTO the settingTestimonialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settingTestimonialDTO,
     * or with status 400 (Bad Request) if the settingTestimonialDTO is not valid,
     * or with status 500 (Internal Server Error) if the settingTestimonialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/setting-testimonials")
    @Timed
    public ResponseEntity<SettingTestimonialDTO> updateSettingTestimonial(@RequestBody SettingTestimonialDTO settingTestimonialDTO) throws URISyntaxException {
        log.debug("REST request to update SettingTestimonial : {}", settingTestimonialDTO);
        if (settingTestimonialDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        SettingTestimonialDTO result = settingTestimonialService.save(settingTestimonialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settingTestimonialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /setting-testimonials : get all the settingTestimonials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of settingTestimonials in body
     */
    @GetMapping("/setting-testimonials")
    @Timed
    public ResponseEntity<List<SettingTestimonialDTO>> getAllSettingTestimonials(Pageable pageable) {
        log.debug("REST request to get a page of SettingTestimonials");
        Page<SettingTestimonialDTO> page = settingTestimonialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/setting-testimonials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /setting-testimonials/:id : get the "id" settingTestimonial.
     *
     * @param id the id of the settingTestimonialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settingTestimonialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/setting-testimonials/{id}")
    @Timed
    public ResponseEntity<SettingTestimonialDTO> getSettingTestimonial(@PathVariable Long id) {
        log.debug("REST request to get SettingTestimonial : {}", id);
        Optional<SettingTestimonialDTO> settingTestimonialDTO = settingTestimonialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingTestimonialDTO);
    }

    /**
     * DELETE  /setting-testimonials/:id : delete the "id" settingTestimonial.
     *
     * @param id the id of the settingTestimonialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/setting-testimonials/{id}")
    @Timed
    public ResponseEntity<Void> deleteSettingTestimonial(@PathVariable Long id) {
        log.debug("REST request to delete SettingTestimonial : {}", id);
        settingTestimonialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
