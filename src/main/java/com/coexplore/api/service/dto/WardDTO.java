package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ward entity.
 */
public class WardDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String location;

    private Long districtId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WardDTO wardDTO = (WardDTO) o;
        if (wardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WardDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", district=" + getDistrictId() +
            "}";
    }
}
