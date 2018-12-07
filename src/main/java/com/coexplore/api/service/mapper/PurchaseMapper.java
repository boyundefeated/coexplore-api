package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.PurchaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Purchase and its DTO PurchaseDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, MembershipMapper.class, PromotionMapper.class})
public interface PurchaseMapper extends EntityMapper<PurchaseDTO, Purchase> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "membership.id", target = "membershipId")
    @Mapping(source = "promotion.id", target = "promotionId")
    PurchaseDTO toDto(Purchase purchase);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "membershipId", target = "membership")
    @Mapping(source = "promotionId", target = "promotion")
    Purchase toEntity(PurchaseDTO purchaseDTO);

    default Purchase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Purchase purchase = new Purchase();
        purchase.setId(id);
        return purchase;
    }
}
