package com.artsgard.flightinfoapi.repository;

import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("airportRepo")
public interface AirportDisplayRepository extends JpaRepository<AirportDisplayEntity, Long> {
   
}
