package com.coexplore.api.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OccupiedRoom entity.
 */
public class OccupiedRoomDTO implements Serializable {

    private Long id;

    private Instant checkIn;

    private Instant checkOut;

    private Long roomId;

    private Long invoiceId;

    private Long reservationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Instant checkIn) {
        this.checkIn = checkIn;
    }

    public Instant getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Instant checkOut) {
        this.checkOut = checkOut;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OccupiedRoomDTO occupiedRoomDTO = (OccupiedRoomDTO) o;
        if (occupiedRoomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), occupiedRoomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OccupiedRoomDTO{" +
            "id=" + getId() +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            ", room=" + getRoomId() +
            ", invoice=" + getInvoiceId() +
            ", reservation=" + getReservationId() +
            "}";
    }
}
