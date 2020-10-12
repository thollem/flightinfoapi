package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;

public interface AirportDisplayExternalService {
      AirportDisplay getAirportInfo(String param);
}
