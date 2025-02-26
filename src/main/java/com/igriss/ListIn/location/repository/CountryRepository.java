package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
}