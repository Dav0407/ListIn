package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.ProductCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductConditionRepository extends JpaRepository<ProductCondition, UUID> {
}