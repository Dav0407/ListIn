package com.igriss.ListIn.attribute.repository;

import com.igriss.ListIn.attribute.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, UUID> {
}