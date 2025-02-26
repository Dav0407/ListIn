package com.igriss.ListIn.location.repository;

import com.igriss.ListIn.location.entity.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountyRepository extends JpaRepository<County, UUID> {

    List<County> findAllByState_Id(UUID stateId);

    Optional<County> findByValueIgnoreCase(String value);
}