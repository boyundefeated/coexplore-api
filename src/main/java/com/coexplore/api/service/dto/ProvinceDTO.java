package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Province entity.
 */
public class ProvinceDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String location;

    private Long countryId;

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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProvinceDTO provinceDTO = (ProvinceDTO) o;
        if (provinceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provinceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvinceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", country=" + getCountryId() +
            "}";
    }
}
