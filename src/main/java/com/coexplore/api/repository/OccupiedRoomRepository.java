package com.coexplore.api.repository;

import com.coexplore.api.domain.OccupiedRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OccupiedRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccupiedRoomRepository extends JpaRepository<OccupiedRoom, Long> {

}
