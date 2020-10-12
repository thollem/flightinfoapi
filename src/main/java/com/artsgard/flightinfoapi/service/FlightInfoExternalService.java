package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;

public interface FlightInfoExternalService {
      FlightInfoExResult getFlightInfo(String params, int offSet);
}
