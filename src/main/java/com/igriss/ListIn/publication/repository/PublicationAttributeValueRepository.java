package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublicationAttributeValueRepository extends JpaRepository<PublicationAttributeValue, UUID> {
    List<PublicationAttributeValue> findByPublication_Id(UUID publicationId);

}
