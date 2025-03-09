package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {

    List<State> findAllByCountry_Id(UUID countryId);

    Optional<State> findByValueIgnoreCase(String value);
    Optional<State> findByValueUzIgnoreCase(String value);
    Optional<State> findByValueRuIgnoreCase(String value);
}