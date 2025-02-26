package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {

    List<State> findAllByCountry_Id(UUID countryId);
}