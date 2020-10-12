package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 
 * @author WillemDragstra
 */
@Service
public interface AirportDisplayService  {
    List<AirportDisplay> findAllAirportDisplays() throws ResourceNotFoundException;
    AirportDisplay findAirportDisplayById(Long id) throws ResourceNotFoundException;
    AirportDisplay findAirportDisplayByUsername(String username) throws ResourceNotFoundException; 
    AirportDisplay saveAirportDisplay(AirportDisplay airportDisplay);
    AirportDisplay updateAirportDisplay(AirportDisplay airportDisplay) throws ResourceNotFoundException;
    void deleteAirportDisplayById(Long id) throws ResourceNotFoundException;
}