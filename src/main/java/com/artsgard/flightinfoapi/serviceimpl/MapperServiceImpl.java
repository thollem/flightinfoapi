package com.artsgard.flightinfoapi.serviceimpl;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.DTO.FlightInfo;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import com.artsgard.flightinfoapi.service.MapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.LoggerFactory;

/**
 *
 * @author WillemDragstra
 *
 */
@Service//("mapperService")
public class MapperServiceImpl implements MapperService {

    org.slf4j.Logger logger;

    ModelMapper modelMapper = new ModelMapper();

    public MapperServiceImpl() {
        this.logger = LoggerFactory.getLogger(MapperServiceImpl.class);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public AirportDisplayEntity mapAirportDisplayDTOToAirportDisplayEntity(AirportDisplay dto) {
        if (dto != null) {
            return modelMapper.map(dto, AirportDisplayEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public AirportDisplay mapAirportDisplayEntityToAirportDisplayDTO(AirportDisplayEntity ent) {
        if (ent != null) {
            return modelMapper.map(ent, AirportDisplay.class);
        } else {
            return null;
        }
    }

    @Override
    public FlightInfoEntity mapFlightInfoDTOToFlightInfoEntity(FlightInfo dto) {
        if (dto != null) {
            return modelMapper.map(dto, FlightInfoEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public FlightInfo mapFlightInfoEntityToFlightInfoDTO(FlightInfoEntity ent) {
        if (ent != null) {
            return modelMapper.map(ent, FlightInfo.class);
        } else {
            return null;
        }
    }
}
