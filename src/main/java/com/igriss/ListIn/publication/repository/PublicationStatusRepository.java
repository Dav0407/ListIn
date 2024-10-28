package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicationStatusRepository extends JpaRepository<PublicationStatus, UUID> {
}