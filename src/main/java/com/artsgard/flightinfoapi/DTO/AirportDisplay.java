package com.artsgard.flightinfoapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AirportDisplay {
    
    private Long id;
     
    //@NotNull
    private String name;
    
    //@NotNull
    private String location;
    
    //@NotNull
    private Double longitude;
    
    //@NotNull
    private Double latitude;
    
    //@NotNull
    private String timeZone;

}
