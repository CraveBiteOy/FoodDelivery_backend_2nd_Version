package com.cravebite.backend_2.models.entities;

// import org.springframework.data.geo.Point;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spatial_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpatialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

}
