package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, UUID> {
}