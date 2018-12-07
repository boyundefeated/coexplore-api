package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Room and its DTO RoomDTO.
 */
@Mapper(componentModel = "spring", uses = {SpaceMapper.class})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {

    @Mapping(source = "space.id", target = "spaceId")
    RoomDTO toDto(Room room);

    @Mapping(source = "spaceId", target = "space")
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "occupiedRooms", ignore = true)
    Room toEntity(RoomDTO roomDTO);

    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
