package com.artsgard.flightinfoapi.repository;

import com.artsgard.flightinfoapi.entity.AirportDisplayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportDisplayRepository extends JpaRepository<AirportDisplayEntity, Long> {
   
}
