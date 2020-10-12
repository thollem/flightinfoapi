package com.artsgard.flightinfoapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airport_display")
@Entity
public class AirportDisplayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull
    @Column(name = "name", nullable = true, unique = false)
    private String name;

    //@NotNull
    @Column(name = "location", nullable = true, unique = false)
    private String location;

    //@NotNull
    @Column(name = "longitude", nullable = true, unique = false)
    private Double longitude;

    @Column(name = "latitude", nullable = true, unique = false)
    private Double latitude;

    //@NotNull
    @Column(name = "time_zone", nullable = true, unique = false)
    private String timeZone;

}
