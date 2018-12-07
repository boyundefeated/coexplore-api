package com.coexplore.api.repository;

import com.coexplore.api.domain.SettingTestimonial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SettingTestimonial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingTestimonialRepository extends JpaRepository<SettingTestimonial, Long> {

}
