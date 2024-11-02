package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.static_entity.PublicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PublicationTypeRepository extends JpaRepository<PublicationType, UUID> {
    @Query(nativeQuery = true, value = "select publication_types.publication_type_id from publication_types where publication_types.name = :name")
    Optional<UUID> getIdByName(String name);
}