package com.artsgard.flightinfoapi.mock;

import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.DTO.FlightInfoExResult;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WillemDragstra
 */
public final class FlightInfoMock {

    private FlightInfoMock() {
    }

    public static FlightInfo generateFlightInfo() {
        FlightInfo flight = new FlightInfo(1L, "faFlightId", "ident", "aircraftType", "filedEte", 1223L, 1234L,
                123, "filedAirSpeedMach", 1234, "route", 1234L, 1234L, 1234L, "diverted", "origin", "destination",
                "originName", "originCity", "destinationName", "destinationCity");

        return flight;
    }

    public static FlightInfoEntity generateFlightInfoEntity() {
        AirportDisplayEntity origin = new AirportDisplayEntity(1L, "name-airport-origin", "location-origin", 12.12D, 12.12D, "time-zone");
        AirportDisplayEntity destination = new AirportDisplayEntity(1L, "name-airport-destination-destination", "", 12.12D, 12.12D, "time-zone");
        FlightInfoEntity flight = new FlightInfoEntity(1L, "faFlightId", "ident", "aircraftType", "filedEte", 1223L, 1234L,
                123, "filedAirSpeedMach", 1234, "route", 1234L, 1234L, 1234L, "diverted", origin, destination,
                "originName", "originCity", "destinationName", "destinationCity");

        return flight;
    }

    public static List<FlightInfo> generateFlights() {
        List<FlightInfo> flights = new ArrayList();
        FlightInfo flight1 = new FlightInfo(1L, "faFlightId1", "ident1", "aircraftType1", "filedEte1", 12231L, 12341L,
                1231, "filedAirSpeedMach1", 12341, "route1", 12341L, 1234L, 12341L, "diverted1", "origin1", "destination1",
                "originName1", "originCity1", "destinationName1", "destinationCity1");
        FlightInfo flight2 = new FlightInfo(2L, "faFlightId2", "ident2", "aircraftType2", "filedEte2", 12232L, 12342L,
                1232, "filedAirSpeedMach2", 12342, "route2", 12342L, 12342L, 12342L, "diverted2", "origin2", "destination2",
                "originName2", "originCity2", "destinationName2", "destinationCity2");
        FlightInfo flight3 = new FlightInfo(3L, "faFlightId3", "ident3", "aircraftType3", "filedEte3", 12233L, 12343L,
                1233, "filedAirSpeedMach3", 12343, "route3", 12343L, 12343L, 12343L, "diverted3", "origin3", "destination3",
                "originName3", "originCity3", "destinationName3", "destinationCity3");
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        return flights;
    }
    
    public static FlightInfoExResult generateFlightInfoExResult() {
        FlightInfoExResult result = new FlightInfoExResult();
        result.setOffset(15);
        result.setFlights(generateFlights());
        return result;
    }
}
