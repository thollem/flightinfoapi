package com.artsgard.flightinfoapi.serviceimpl;

import com.artsgard.flightinfoapi.DTO.AirportDisplay;
import com.artsgard.flightinfoapi.service.MapperService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artsgard.flightinfoapi.exception.ResourceNotFoundException;
import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import com.artsgard.flightinfoapi.service.AirportDisplayService;
import java.util.ArrayList;
import java.util.Optional;
import com.artsgard.flightinfoapi.repository.AirportDisplayRepository;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author WillemDragstra
 */
@Service//("airportService")
public class AirportDisplayServiceImpl implements AirportDisplayService {

    org.slf4j.Logger logger;

    @Autowired
    //@Qualifier("mapperService")
    private MapperService mapperService;

    @Autowired
    //@Qualifier("airportRepo")
    private AirportDisplayRepository displayRepo;

    public AirportDisplayServiceImpl() { }

    @Override
    public List<AirportDisplay> findAllAirportDisplays() throws ResourceNotFoundException {
        List<AirportDisplayEntity> displays = displayRepo.findAll();
        if (displays.isEmpty()) {
            throw new ResourceNotFoundException("no Airport Display found!");
        } else {
            List<AirportDisplay> displayList = new ArrayList();

            for (AirportDisplayEntity disp : displays) {
                AirportDisplay dto = mapperService.mapAirportDisplayEntityToAirportDisplayDTO(disp);
                displayList.add(dto);
            }
            return displayList;
        }
    }

    @Override
    public AirportDisplay findAirportDisplayById(Long id) throws ResourceNotFoundException {
        Optional<AirportDisplayEntity> opAirportDisplay = displayRepo.findById(id);
        if (opAirportDisplay.isPresent()) {
            return mapperService.mapAirportDisplayEntityToAirportDisplayDTO(opAirportDisplay.get());
        } else {
            throw new ResourceNotFoundException("no airportDisplay found with id: " + id);
        }
    }

    @Override
    public AirportDisplay findAirportDisplayByUsername(String username) throws ResourceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AirportDisplay saveAirportDisplay(AirportDisplay airportDisplay) {
        AirportDisplayEntity entity = mapperService.mapAirportDisplayDTOToAirportDisplayEntity(airportDisplay);
        return mapperService.mapAirportDisplayEntityToAirportDisplayDTO(displayRepo.save(entity));
    }

    @Override
    public AirportDisplay updateAirportDisplay(AirportDisplay airportDisplay) throws ResourceNotFoundException {
        Optional<AirportDisplayEntity> opAirportDisplay = displayRepo.findById(airportDisplay.getId());
        if (opAirportDisplay.isPresent()) {
            return mapperService.mapAirportDisplayEntityToAirportDisplayDTO(opAirportDisplay.get());
        } else {
            throw new ResourceNotFoundException("no airportDisplay found with id: " + airportDisplay.getId());
        }

    }

    @Override
    public void deleteAirportDisplayById(Long id) throws ResourceNotFoundException {
        Optional<AirportDisplayEntity> opAirportDisplay = displayRepo.findById(id);

        if (opAirportDisplay.isPresent()) {
            displayRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("no Airport Display found with id: " + id);
        }
    }
}
