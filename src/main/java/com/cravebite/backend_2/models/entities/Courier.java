package com.cravebite.backend_2.models.entities;

import com.cravebite.backend_2.models.enums.CourierStatus;
import com.cravebite.backend_2.models.enums.NavigationMode;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courier")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CourierStatus status;
    private boolean availability;
    private NavigationMode mode;

    private boolean firstLogin = true;

    @Column(name = "location_id")
    private Long locationId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

}
