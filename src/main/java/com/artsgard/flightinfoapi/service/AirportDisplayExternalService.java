package com.artsgard.flightinfoapi.service;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import org.springframework.stereotype.Service;

@Service
public interface AirportDisplayExternalService {
      AirportDisplay getAirportInfo(String param);
}
