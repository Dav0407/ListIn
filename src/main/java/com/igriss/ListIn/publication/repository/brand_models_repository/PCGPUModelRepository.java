package com.igriss.ListIn.publication.repository.brand_models_repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.PCBrandModel;
import com.igriss.ListIn.publication.entity.brand_models.PCGPUModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PCGPUModelRepository extends JpaRepository<PCGPUModel, UUID> {
    List<PCGPUModel> findByAttributeValue(AttributeValue attributeValue);
}