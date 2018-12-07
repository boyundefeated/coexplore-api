package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "price")
    private Double price;

    @Column(name = "seat")
    private Integer seat;

    @ManyToOne
    @JsonIgnoreProperties("rooms")
    private Space space;

    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "room")
    private Set<OccupiedRoom> occupiedRooms = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Room roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getPrice() {
        return price;
    }

    public Room price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSeat() {
        return seat;
    }

    public Room seat(Integer seat) {
        this.seat = seat;
        return this;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Space getSpace() {
        return space;
    }

    public Room space(Space space) {
        this.space = space;
        return this;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Room reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Room addReservations(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setRoom(this);
        return this;
    }

    public Room removeReservations(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setRoom(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<OccupiedRoom> getOccupiedRooms() {
        return occupiedRooms;
    }

    public Room occupiedRooms(Set<OccupiedRoom> occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
        return this;
    }

    public Room addOccupiedRoom(OccupiedRoom occupiedRoom) {
        this.occupiedRooms.add(occupiedRoom);
        occupiedRoom.setRoom(this);
        return this;
    }

    public Room removeOccupiedRoom(OccupiedRoom occupiedRoom) {
        this.occupiedRooms.remove(occupiedRoom);
        occupiedRoom.setRoom(null);
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
        Room room = (Room) o;
        if (room.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", price=" + getPrice() +
            ", seat=" + getSeat() +
            "}";
    }
}
