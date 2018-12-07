package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.SpaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Space and its DTO SpaceDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface SpaceMapper extends EntityMapper<SpaceDTO, Space> {

    @Mapping(source = "location.id", target = "locationId")
    SpaceDTO toDto(Space space);

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "rooms", ignore = true)
    Space toEntity(SpaceDTO spaceDTO);

    default Space fromId(Long id) {
        if (id == null) {
            return null;
        }
        Space space = new Space();
        space.setId(id);
        return space;
    }
}
