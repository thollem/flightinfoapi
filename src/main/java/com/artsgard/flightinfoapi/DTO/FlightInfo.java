package com.artsgard.flightinfoapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfo {

    private Long id;

    @JsonProperty("faFlightID")
    //@NotNull
    private String faFlightId;

    //@NotNull
    private String ident;

    @JsonProperty("aircrafttype")
    //@NotNull
    private String aircraftType;

    //@NotNull
    @JsonProperty("filed_ete")
    private String filedEte; // ? date hour

    //@NotNull
    @JsonProperty("filed_time")
    private long filedTime;

    //@NotNull
    @JsonProperty("filed_departuretime")
    private long filedDepartureTime; //FxmlTimestamp

    //@NotNull
    @JsonProperty("filed_airspeed_kts")
    private int filedAirSpeedKts;

    //@NotNull
    @JsonProperty("filed_airspeed_mach")
    private String filedAirSpeedMach; //int

    //@NotNull
    @JsonProperty("filed_altitude")
    private int filedAltitude;

    //@NotNull
    private String route;

    //@NotNull
    @JsonProperty("actualdeparturetime")
    private long actualDepartureTime; //FxmlTimestamp

    //@NotNull
    @JsonProperty("estimatedarrivaltime")
    private long estimatedArrivalTime; //FxmlTimestamp

    //@NotNull
    @JsonProperty("actualarrivaltime")
    private long actualArrivalTime; //FxmlTimestamp

    //@NotNull
    private String diverted; //string

    //@NotNull
    private String origin; //AirportDisplay

    //@NotNull
    private String destination; //AirportDisplay

    //@NotNull
    @JsonProperty("originName")
    private String originName; //AirportDisplay 

    //@NotNull
    @JsonProperty("originCity")
    private String originCity; //AirportDisplay 

    //@NotNull  
    @JsonProperty("destinationName")
    private String destinationName; //AirportDisplay 

    //@NotNull
    @JsonProperty("destinationCity")
    private String destinationCity; //AirportDisplay 

}
