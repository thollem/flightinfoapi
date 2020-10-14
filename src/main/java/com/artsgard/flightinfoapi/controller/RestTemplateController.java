package com.artsgard.flightinfoapi.controller;

import com.artsgard.flightinfoapi.DTO.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class RestTemplateController {

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;
    
    private final static String BASEURL = "https://flightxml.flightaware.com/json/FlightXML2/FlightInfoEx";
  
    @RequestMapping(path = "/flightInfoTemplate")
    public ResponseEntity<JsonWrapper> getAirportBoards(@RequestParam String ident) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASEURL)
                .queryParam("ident", ident)
                .queryParam("howMany", 15)
                .queryParam("offset", 0);
        try {
            JsonWrapper result = restTemplate.getForObject(uriBuilder.toUriString(), JsonWrapper.class);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            System.err.println("Server IOException: " + ex);
            return null;
        }
    }
}
