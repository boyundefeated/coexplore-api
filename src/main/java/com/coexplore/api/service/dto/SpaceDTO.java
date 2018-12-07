package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Space entity.
 */
public class SpaceDTO implements Serializable {

    private Long id;

    private String name;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpaceDTO spaceDTO = (SpaceDTO) o;
        if (spaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location=" + getLocationId() +
            "}";
    }
}
