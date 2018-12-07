package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    private Customer customer;

    @OneToMany(mappedBy = "reservation")
    private Set<OccupiedRoom> occupiedRooms = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Reservation fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public Reservation toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Room getRoom() {
        return room;
    }

    public Reservation room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Reservation customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OccupiedRoom> getOccupiedRooms() {
        return occupiedRooms;
    }

    public Reservation occupiedRooms(Set<OccupiedRoom> occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
        return this;
    }

    public Reservation addOccupiedRoom(OccupiedRoom occupiedRoom) {
        this.occupiedRooms.add(occupiedRoom);
        occupiedRoom.setReservation(this);
        return this;
    }

    public Reservation removeOccupiedRoom(OccupiedRoom occupiedRoom) {
        this.occupiedRooms.remove(occupiedRoom);
        occupiedRoom.setReservation(null);
        return this;
    }

    public void setOccupiedRooms(Set<OccupiedRoom> occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
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
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            "}";
    }
}
