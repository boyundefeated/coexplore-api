package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.SettingContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SettingContent and its DTO SettingContentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SettingContentMapper extends EntityMapper<SettingContentDTO, SettingContent> {



    default SettingContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SettingContent settingContent = new SettingContent();
        settingContent.setId(id);
        return settingContent;
    }
}
