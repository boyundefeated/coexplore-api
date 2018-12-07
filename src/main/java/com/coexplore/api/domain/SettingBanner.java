package com.coexplore.api.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SettingBanner.
 */
@Entity
@Table(name = "setting_banner")
public class SettingBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "main_text")
    private String mainText;

    @Column(name = "sub_text")
    private String subText;

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

    public SettingBanner image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMainText() {
        return mainText;
    }

    public SettingBanner mainText(String mainText) {
        this.mainText = mainText;
        return this;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public SettingBanner subText(String subText) {
        this.subText = subText;
        return this;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public Integer getOrder() {
        return order;
    }

    public SettingBanner order(Integer order) {
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
        SettingBanner settingBanner = (SettingBanner) o;
        if (settingBanner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingBanner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingBanner{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", mainText='" + getMainText() + "'" +
            ", subText='" + getSubText() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
