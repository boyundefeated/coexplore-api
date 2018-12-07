package com.coexplore.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Space.
 */
@Entity
@Table(name = "space")
public class Space implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "space")
    private Set<Room> rooms = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Space name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public Space location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Space rooms(Set<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public Space addRoom(Room room) {
        this.rooms.add(room);
        room.setSpace(this);
        return this;
    }

    public Space removeRoom(Room room) {
        this.rooms.remove(room);
        room.setSpace(null);
        return this;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
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
        Space space = (Space) o;
        if (space.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), space.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Space{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
