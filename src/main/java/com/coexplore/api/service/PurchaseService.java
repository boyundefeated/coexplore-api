package com.coexplore.api.service;

import com.coexplore.api.service.dto.PurchaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Purchase.
 */
public interface PurchaseService {

    /**
     * Save a purchase.
     *
     * @param purchaseDTO the entity to save
     * @return the persisted entity
     */
    PurchaseDTO save(PurchaseDTO purchaseDTO);

    /**
     * Get all the purchases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" purchase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchaseDTO> findOne(Long id);

    /**
     * Delete the "id" purchase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
