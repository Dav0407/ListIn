package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicationAttributeValueRepository extends JpaRepository<PublicationAttributeValue, UUID> {

}
