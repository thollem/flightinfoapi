package com.artsgard.flightinfoapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWrapper {
    
    @JsonProperty("FlightInfoExResult")
    private FlightInfoExResult flightInfoExResult;
}
