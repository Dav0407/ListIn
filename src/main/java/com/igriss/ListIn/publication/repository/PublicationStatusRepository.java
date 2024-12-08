package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.static_entity.PublicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PublicationStatusRepository extends JpaRepository<PublicationStatus, UUID> {
    @Query(nativeQuery = true, value = "select publication_statuses.publication_status_id from publication_statuses where publication_statuses.name = :publication")
    Optional<UUID> getIdByName(String publication);
}
