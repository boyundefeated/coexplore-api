package com.coexplore.api.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SettingTestimonial.
 */
@Entity
@Table(name = "setting_testimonial")
public class SettingTestimonial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "jhi_order")
    private Integer order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public SettingTestimonial image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public SettingTestimonial comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getOrder() {
        return order;
    }

    public SettingTestimonial order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
        SettingTestimonial settingTestimonial = (SettingTestimonial) o;
        if (settingTestimonial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingTestimonial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingTestimonial{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", comment='" + getComment() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
