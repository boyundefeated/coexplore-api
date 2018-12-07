package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SettingBanner entity.
 */
public class SettingBannerDTO implements Serializable {

    private Long id;

    private String image;

    private String mainText;

    private String subText;

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

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
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

        SettingBannerDTO settingBannerDTO = (SettingBannerDTO) o;
        if (settingBannerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingBannerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingBannerDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", mainText='" + getMainText() + "'" +
            ", subText='" + getSubText() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
