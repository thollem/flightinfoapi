package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 
 * @author WillemDragstra
 */
@Service
public interface FlightInfoService  {
    List<FlightInfo> findAllFlightInfos() throws ResourceNotFoundException;
    FlightInfo findFlightInfoById(Long id) throws ResourceNotFoundException;
    List<FlightInfo> findFlightsInfoByIdent(String ident) throws ResourceNotFoundException;
    FlightInfo saveFlightInfo(FlightInfo flightInfo, AirportDisplay origin,  AirportDisplay destination);
    List<FlightInfo> saveAllFlightInfo(List<FlightInfo> flightInfos);
    FlightInfo updateFlightInfo(FlightInfo flightInfo) throws ResourceNotFoundException;
    void deleteFlightInfoById(Long id) throws ResourceNotFoundException;
}