package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.static_entity.PublicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PublicationStatusRepository extends JpaRepository<PublicationStatus, UUID> {
}
