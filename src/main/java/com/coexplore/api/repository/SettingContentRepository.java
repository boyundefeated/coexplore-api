package com.coexplore.api.repository;

import com.coexplore.api.domain.SettingContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SettingContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SettingContentRepository extends JpaRepository<SettingContent, Long> {

}
