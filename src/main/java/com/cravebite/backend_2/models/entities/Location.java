package com.cravebite.backend_2.models.entities;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
// @Data
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "location_point", columnDefinition = "geometry(Point,4326)")
    private Point geom;

    @JsonIgnore
    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Courier courier;

    @JsonIgnore
    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantOwner restaurantOwner;

}
