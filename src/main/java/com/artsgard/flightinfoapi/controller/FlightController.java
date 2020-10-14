package com.artsgard.flightinfoapi.controller;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.service.FlightInfoExternalService;
import com.artsgard.flightinfoapi.service.FlightInfoService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author WillemDragstra
 */
@RestController
@RequestMapping("")
public class FlightController {

    org.slf4j.Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    //@Qualifier("flightExtService")
    private FlightInfoExternalService flightExtService;
    
    @Autowired
    //@Qualifier("flightService")
    private FlightInfoService flightService;

    @GetMapping(path = "/findFlightInfoByTailnumber/{tailnumber}", produces = "application/json")
    public ResponseEntity<?> findFlightInfoByTailnumber(@PathVariable("tailnumber") String tailnumber) {
        int offset = 0;
        List<FlightInfo> flights = new ArrayList();
        for (int i = 0; i < 10; i++) {
            FlightInfoExResult infoResult = flightExtService.getFlightInfo(tailnumber, offset);
            offset = infoResult.getOffset();
            flights.addAll(flightService.saveAllFlightInfo(infoResult.getFlights()));
        }
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    
    @GetMapping(path = "/findFlightInfosByIdent/{ident}", produces = "application/json")
    public ResponseEntity<List<FlightInfo>> findFlightInfosByIdent(@PathVariable("ident") String ident) {
        return new ResponseEntity<List<FlightInfo>>(flightService.findFlightsInfoByIdent(ident), HttpStatus.OK);
    }
    
}
