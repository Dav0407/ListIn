package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.AttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttributeKeyRepository extends JpaRepository<AttributeKey, UUID> {
}