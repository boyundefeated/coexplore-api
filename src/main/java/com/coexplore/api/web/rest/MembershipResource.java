package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.service.MembershipService;
import com.coexplore.api.service.dto.MembershipDTO;
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
 * REST controller for managing Membership.
 */
@RestController
@RequestMapping("/api")
public class MembershipResource {

    private final Logger log = LoggerFactory.getLogger(MembershipResource.class);

    private static final String ENTITY_NAME = "membership";

    private final MembershipService membershipService;

    public MembershipResource(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * POST  /memberships : Create a new membership.
     *
     * @param membershipDTO the membershipDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membershipDTO, or with status 400 (Bad Request) if the membership has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/memberships")
    @Timed
    public ResponseEntity<MembershipDTO> createMembership(@RequestBody MembershipDTO membershipDTO) throws URISyntaxException {
        log.debug("REST request to save Membership : {}", membershipDTO);
        if (membershipDTO.getId() != null) {
            throw new RuntimeException("A new membership cannot already have an ID");
        }
        MembershipDTO result = membershipService.save(membershipDTO);
        return ResponseEntity.created(new URI("/api/memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /memberships : Updates an existing membership.
     *
     * @param membershipDTO the membershipDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membershipDTO,
     * or with status 400 (Bad Request) if the membershipDTO is not valid,
     * or with status 500 (Internal Server Error) if the membershipDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/memberships")
    @Timed
    public ResponseEntity<MembershipDTO> updateMembership(@RequestBody MembershipDTO membershipDTO) throws URISyntaxException {
        log.debug("REST request to update Membership : {}", membershipDTO);
        if (membershipDTO.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        MembershipDTO result = membershipService.save(membershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /memberships : get all the memberships.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memberships in body
     */
    @GetMapping("/memberships")
    @Timed
    public ResponseEntity<List<MembershipDTO>> getAllMemberships(Pageable pageable) {
        log.debug("REST request to get a page of Memberships");
        Page<MembershipDTO> page = membershipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/memberships");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /memberships/:id : get the "id" membership.
     *
     * @param id the id of the membershipDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membershipDTO, or with status 404 (Not Found)
     */
    @GetMapping("/memberships/{id}")
    @Timed
    public ResponseEntity<MembershipDTO> getMembership(@PathVariable Long id) {
        log.debug("REST request to get Membership : {}", id);
        Optional<MembershipDTO> membershipDTO = membershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipDTO);
    }

    /**
     * DELETE  /memberships/:id : delete the "id" membership.
     *
     * @param id the id of the membershipDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/memberships/{id}")
    @Timed
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        log.debug("REST request to delete Membership : {}", id);
        membershipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
