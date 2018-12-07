package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.SettingBannerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SettingBanner and its DTO SettingBannerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SettingBannerMapper extends EntityMapper<SettingBannerDTO, SettingBanner> {



    default SettingBanner fromId(Long id) {
        if (id == null) {
            return null;
        }
        SettingBanner settingBanner = new SettingBanner();
        settingBanner.setId(id);
        return settingBanner;
    }
}
