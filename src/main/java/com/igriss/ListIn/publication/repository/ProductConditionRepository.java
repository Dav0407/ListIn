package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.static_entity.ProductCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductConditionRepository extends JpaRepository<ProductCondition, UUID> {
    @Query(nativeQuery = true, value = "select product_conditions.condition_id from product_conditions where product_conditions.condition_name = :productCondition")
    Optional<UUID> getIdByName(String productCondition);
}