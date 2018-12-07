package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.ReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class, CustomerMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "customer.id", target = "customerId")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(target = "occupiedRooms", ignore = true)
    Reservation toEntity(ReservationDTO reservationDTO);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
