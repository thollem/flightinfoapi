package com.artsgard.flightinfoapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight_info")
@Entity
public class FlightInfoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
        
    //@JsonProperty("faFlightID")
    //@NotNull
    @Column(name = "fa_flight_id", nullable = true, unique = false)
    private String faFlightId;

    //@NotNull
    @Column(name = "ident", nullable = true, unique = false)
    private String ident;

    //@NotNull
    @Column(name = "aircraft_type", nullable = true, unique = false)
    private String aircraftType;
    
    //@NotNull
    @Column(name = "filed_ete", nullable = true, unique = false)
    private String filedEte;
    
    //@NotNull
    @Column(name = "filed_time", nullable = true, unique = false)
    private long filedTime;
   
    //@NotNull
    @Column(name = "filed_departure_time", nullable = true, unique = false)
    private long filedDepartureTime;
    
    //@NotNull
    @Column(name = "filed_air_speed_kts", nullable = true, unique = false)
    private int filedAirSpeedKts;

    //@NotNull
    @Column(name = "filed_air_speed_mach", nullable = true, unique = false)
    private String filedAirSpeedMach;

    //@NotNull
    @Column(name = "filed_altitude", nullable = true, unique = false)
    private int filedAltitude;
    
    //@NotNull
    @Column(name = "route", nullable = true, unique = false)
    private String route;
    
    //@NotNull
    @Column(name = "actual_departure_time", nullable = true, unique = false)
    private long actualDepartureTime;
    
    //@NotNull
    @Column(name = "estimated_arrival_time", nullable = true, unique = false)
    private long estimatedArrivalTime;
    
    //@NotNull
    @Column(name = "actual_arrival_time", nullable = true, unique = false)
    private long actualArrivalTime;
    
    //@NotNull
    @Column(name = "diverted", nullable = true, unique = false)
    private String diverted;

     //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id")
    private AirportDisplayEntity origin;
    
     //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private AirportDisplayEntity destination;
    
    //@NotNull
    @Column(name = "origin_name", nullable = true, unique = false)
    private String originName; //AirportDisplay 
    
    //@NotNull
    @Column(name = "origin_city", nullable = true, unique = false)
    private String originCity; //AirportDisplay 
    
     //@NotNull
    @Column(name = "destination_name", nullable = true, unique = false)
    private String destinationName; //AirportDisplay 
    
     //@NotNull
    @Column(name = "destination_city", nullable = true, unique = false)
    private String destinationCity; //AirportDisplay 

}
