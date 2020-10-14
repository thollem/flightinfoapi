package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import org.springframework.stereotype.Service;

@Service
public interface FlightInfoExternalService {
      FlightInfoExResult getFlightInfo(String tailNumber, int offSet);
}
