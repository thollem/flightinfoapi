package com.artsgard.flightinfoapi.repository;

import com.artsgard.flightinfoapi.entity.FlightInfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("flightRepo")
public interface FlightInfoRepository extends JpaRepository<FlightInfoEntity, Long> {
    List<FlightInfoEntity> findByIdent(String ident);
}
