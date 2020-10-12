package com.artsgard.flightinfoapi.serviceimpl;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import com.artsgard.flightinfoapi.repository.AirportDisplayRepository;
import com.artsgard.flightinfoapi.repository.FlightInfoRepository;
import com.artsgard.flightinfoapi.service.AirportDisplayExternalService;
import com.artsgard.flightinfoapi.service.FlightInfoService;
import com.artsgard.flightinfoapi.service.MapperService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author artsgard
 */
@Service
public class FlightInfoServiceImpl implements FlightInfoService {

    org.slf4j.Logger logger;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private FlightInfoRepository flightRepo;

    @Autowired
    private AirportDisplayRepository airportRepo;

    @Autowired
    private AirportDisplayExternalService airportExternalService;

    public FlightInfoServiceImpl() {

    }

    @Override
    public List<FlightInfo> findAllFlightInfos() throws ResourceNotFoundException {
        List<FlightInfoEntity> flights = flightRepo.findAll();
        if (flights.isEmpty()) {
            throw new ResourceNotFoundException("no Airport Display found!");
        } else {
            List<FlightInfo> displayList = new ArrayList();

            for (FlightInfoEntity disp : flights) {
                FlightInfo dto = mapperService.mapFlightInfoEntityToFlightInfoDTO(disp);
                displayList.add(dto);
            }
            return displayList;
        }
    }

    @Override
    public FlightInfo findFlightInfoById(Long id) throws ResourceNotFoundException {
        Optional<FlightInfoEntity> opFlightInfo = flightRepo.findById(id);
        if (opFlightInfo.isPresent()) {
            return mapperService.mapFlightInfoEntityToFlightInfoDTO(opFlightInfo.get());
        } else {
            throw new ResourceNotFoundException("no Flight Info found with id: " + id);
        }
    }

    @Override
    public List<FlightInfo> findFlightsInfoByIdent(String ident) throws ResourceNotFoundException {
        List<FlightInfoEntity> flights = flightRepo.findByIdent(ident);
        List<FlightInfo> list = new ArrayList();
        if (flights.isEmpty()) {
             throw new ResourceNotFoundException("no Flight Info found with ident: " + ident);
        } else {
           
             for (FlightInfoEntity disp : flights) {
                FlightInfo dto = mapperService.mapFlightInfoEntityToFlightInfoDTO(disp);
                list.add(dto);
            }
            return list;
        }
    }

    @Override
    public FlightInfo saveFlightInfo(FlightInfo flightInfo, AirportDisplay origin, AirportDisplay destination) {
        FlightInfoEntity entity = mapperService.mapFlightInfoDTOToFlightInfoEntity(flightInfo);

        AirportDisplayEntity orgEnt = airportRepo.save(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(origin));
        AirportDisplayEntity destEnt = airportRepo.save(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(destination));

        entity.setOrigin(orgEnt);
        entity.setDestination(destEnt);

        return mapperService.mapFlightInfoEntityToFlightInfoDTO(flightRepo.save(entity));
    }

    @Override
    public List<FlightInfo> saveAllFlightInfo(List<FlightInfo> flightInfos) {

        List<FlightInfo> list = new ArrayList();
        for (FlightInfo flight : flightInfos) {
            AirportDisplay origin = airportExternalService.getAirportInfo(flight.getOrigin());
            AirportDisplay destination = airportExternalService.getAirportInfo(flight.getDestination());

            FlightInfoEntity flightEnt = mapperService.mapFlightInfoDTOToFlightInfoEntity(flight);

            AirportDisplayEntity orgEnt = airportRepo.save(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(origin));
            AirportDisplayEntity destEnt = airportRepo.save(mapperService.mapAirportDisplayDTOToAirportDisplayEntity(destination));

            flightEnt.setOrigin(orgEnt);
            flightEnt.setDestination(destEnt);

            flightRepo.save(flightEnt);

            list.add(mapperService.mapFlightInfoEntityToFlightInfoDTO(flightEnt));
        }
        return list;
    }

    @Override
    public FlightInfo updateFlightInfo(FlightInfo flightInfo) throws ResourceNotFoundException {
        Optional<FlightInfoEntity> opFlightInfo = flightRepo.findById(flightInfo.getId());
        if (opFlightInfo.isPresent()) {
            return mapperService.mapFlightInfoEntityToFlightInfoDTO(opFlightInfo.get());
        } else {
            throw new ResourceNotFoundException("no Flight Info found with id: " + flightInfo.getId());
        }
    }

    @Override
    public void deleteFlightInfoById(Long id) throws ResourceNotFoundException {
        Optional<FlightInfoEntity> opFlightInfo = flightRepo.findById(id);

        if (opFlightInfo.isPresent()) {
            flightRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("no Airport Display found with id: " + id);
        }
    }
}
