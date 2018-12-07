package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.MembershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Membership and its DTO MembershipDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MembershipMapper extends EntityMapper<MembershipDTO, Membership> {



    default Membership fromId(Long id) {
        if (id == null) {
            return null;
        }
        Membership membership = new Membership();
        membership.setId(id);
        return membership;
    }
}
