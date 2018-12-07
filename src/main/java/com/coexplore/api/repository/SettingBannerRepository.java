package com.coexplore.api.repository;

import com.coexplore.api.domain.SettingBanner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SettingBanner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingBannerRepository extends JpaRepository<SettingBanner, Long> {

}
