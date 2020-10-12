package com.artsgard.flightinfoapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author artsgard
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightInfoExResult {
    
    @JsonProperty("next_offset")
    private int offset;
    
    @JsonProperty("flights")
    private List <FlightInfo> flights;
}
