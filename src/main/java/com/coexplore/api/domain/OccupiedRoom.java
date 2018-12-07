package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A OccupiedRoom.
 */
@Entity
@Table(name = "occupied_room")
public class OccupiedRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in")
    private Instant checkIn;

    @Column(name = "check_out")
    private Instant checkOut;

    @ManyToOne
    @JsonIgnoreProperties("occupiedRooms")
    private Room room;

    @OneToOne    @JoinColumn(unique = true)
    private Invoice invoice;

    @ManyToOne
    @JsonIgnoreProperties("occupiedRooms")
    private Reservation reservation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCheckIn() {
        return checkIn;
    }

    public OccupiedRoom checkIn(Instant checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public void setCheckIn(Instant checkIn) {
        this.checkIn = checkIn;
    }

    public Instant getCheckOut() {
        return checkOut;
    }

    public OccupiedRoom checkOut(Instant checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public void setCheckOut(Instant checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return room;
    }

    public OccupiedRoom room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public OccupiedRoom invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public OccupiedRoom reservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OccupiedRoom occupiedRoom = (OccupiedRoom) o;
        if (occupiedRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), occupiedRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OccupiedRoom{" +
            "id=" + getId() +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            "}";
    }
}
