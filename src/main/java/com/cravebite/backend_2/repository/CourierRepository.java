package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.enums.CourierStatus;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findByUserId(Long userId);

    List<Courier> findByStatusAndAvailability(CourierStatus status, boolean availability);

    @Query(value = "SELECT c.* FROM courier c JOIN location l ON c.location_id = l.id WHERE ST_Distance(CAST(l.location_point AS geography), CAST(:location AS geography)) <= :radius", nativeQuery = true)
    List<Courier> findNearbyCouriers(@Param("location") Point location, @Param("radius") Double radius);

    @Query(value = "SELECT ST_Distance(CAST(:point1 AS geography), CAST(:point2 AS geography))", nativeQuery = true)
    Double calculateDistance(@Param("point1") Point point1, @Param("point2") Point point2);

}
