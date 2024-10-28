package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
}