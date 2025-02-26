package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    List<City> findAllByState_Id(UUID stateId);

    Optional<City> findByValueIgnoreCase(String value);
}