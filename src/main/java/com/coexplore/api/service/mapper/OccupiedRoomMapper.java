package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.OccupiedRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OccupiedRoom and its DTO OccupiedRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class, InvoiceMapper.class, ReservationMapper.class})
public interface OccupiedRoomMapper extends EntityMapper<OccupiedRoomDTO, OccupiedRoom> {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "reservation.id", target = "reservationId")
    OccupiedRoomDTO toDto(OccupiedRoom occupiedRoom);

    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "reservationId", target = "reservation")
    OccupiedRoom toEntity(OccupiedRoomDTO occupiedRoomDTO);

    default OccupiedRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        OccupiedRoom occupiedRoom = new OccupiedRoom();
        occupiedRoom.setId(id);
        return occupiedRoom;
    }
}
