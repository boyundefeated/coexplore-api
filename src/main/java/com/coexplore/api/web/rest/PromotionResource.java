package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.PromotionService;
import com.coexplore.api.service.dto.PromotionDTO;
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
 * REST controller for managing Promotion.
 */
@RestController
@RequestMapping("/api")
public class PromotionResource {

    private final Logger log = LoggerFactory.getLogger(PromotionResource.class);

    private static final String ENTITY_NAME = "promotion";

    private final PromotionService promotionService;

    public PromotionResource(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    /**
     * POST  /promotions : Create a new promotion.
     *
     * @param promotionDTO the promotionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new promotionDTO, or with status 400 (Bad Request) if the promotion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/promotions")
    @Timed
    public ResponseEntity<PromotionDTO> createPromotion(@RequestBody PromotionDTO promotionDTO) throws URISyntaxException {
        log.debug("REST request to save Promotion : {}", promotionDTO);
        if (promotionDTO.getId() != null) {
            throw new RuntimeException("A new promotion cannot already have an ID");
        }
        PromotionDTO result = promotionService.save(promotionDTO);
        return ResponseEntity.created(new URI("/api/promotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promotions : Updates an existing promotion.
     *
     * @param promotionDTO the promotionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated promotionDTO,
     * or with status 400 (Bad Request) if the promotionDTO is not valid,
     * or with status 500 (Internal Server Error) if the promotionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/promotions")
    @Timed
    public ResponseEntity<PromotionDTO> updatePromotion(@RequestBody PromotionDTO promotionDTO) throws URISyntaxException {
        log.debug("REST request to update Promotion : {}", promotionDTO);
        if (promotionDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        PromotionDTO result = promotionService.save(promotionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, promotionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promotions : get all the promotions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of promotions in body
     */
    @GetMapping("/promotions")
    @Timed
    public ResponseEntity<List<PromotionDTO>> getAllPromotions(Pageable pageable) {
        log.debug("REST request to get a page of Promotions");
        Page<PromotionDTO> page = promotionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/promotions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /promotions/:id : get the "id" promotion.
     *
     * @param id the id of the promotionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the promotionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/promotions/{id}")
    @Timed
    public ResponseEntity<PromotionDTO> getPromotion(@PathVariable Long id) {
        log.debug("REST request to get Promotion : {}", id);
        Optional<PromotionDTO> promotionDTO = promotionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(promotionDTO);
    }

    /**
     * DELETE  /promotions/:id : delete the "id" promotion.
     *
     * @param id the id of the promotionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/promotions/{id}")
    @Timed
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        log.debug("REST request to delete Promotion : {}", id);
        promotionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
