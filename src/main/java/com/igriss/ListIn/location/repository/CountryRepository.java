package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByValueIgnoreCase(String value);
    Optional<Country> findByValueUzIgnoreCase(String value);
    Optional<Country> findByValueRuIgnoreCase(String value);
}