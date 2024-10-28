package com.igriss.ListIn.attribute.repository;

import com.igriss.ListIn.attribute.entity.AttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttributeKeyRepository extends JpaRepository<AttributeKey, UUID> {
}