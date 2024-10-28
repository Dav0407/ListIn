package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicationTypeRepository extends JpaRepository<PublicationType, UUID> {
}