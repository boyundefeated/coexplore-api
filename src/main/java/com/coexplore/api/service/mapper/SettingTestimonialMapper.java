package com.coexplore.api.service.mapper;

import com.coexplore.api.domain.*;
import com.coexplore.api.service.dto.SettingTestimonialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SettingTestimonial and its DTO SettingTestimonialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SettingTestimonialMapper extends EntityMapper<SettingTestimonialDTO, SettingTestimonial> {



    default SettingTestimonial fromId(Long id) {
        if (id == null) {
            return null;
        }
        SettingTestimonial settingTestimonial = new SettingTestimonial();
        settingTestimonial.setId(id);
        return settingTestimonial;
    }
}
