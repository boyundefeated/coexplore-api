package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SettingTestimonial entity.
 */
public class SettingTestimonialDTO implements Serializable {

    private Long id;

    private String image;

    private String comment;

    private Integer order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SettingTestimonialDTO settingTestimonialDTO = (SettingTestimonialDTO) o;
        if (settingTestimonialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingTestimonialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingTestimonialDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", comment='" + getComment() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
