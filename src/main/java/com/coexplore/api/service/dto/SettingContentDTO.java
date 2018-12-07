package com.coexplore.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SettingContent entity.
 */
public class SettingContentDTO implements Serializable {

    private Long id;

    private String item;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SettingContentDTO settingContentDTO = (SettingContentDTO) o;
        if (settingContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingContentDTO{" +
            "id=" + getId() +
            ", item='" + getItem() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
