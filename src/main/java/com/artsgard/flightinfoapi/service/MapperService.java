package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import org.springframework.stereotype.Service;

@Service
public interface MapperService  {
   AirportDisplayEntity mapAirportDisplayDTOToAirportDisplayEntity(AirportDisplay dto);
   AirportDisplay mapAirportDisplayEntityToAirportDisplayDTO(AirportDisplayEntity ent);
   
   FlightInfoEntity mapFlightInfoDTOToFlightInfoEntity(FlightInfo dto);
   FlightInfo mapFlightInfoEntityToFlightInfoDTO(FlightInfoEntity ent);
}