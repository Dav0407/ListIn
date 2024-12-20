package com.igriss.ListIn.publication.repository.brand_models_repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.LaptopGPUModel;
import com.igriss.ListIn.publication.entity.brand_models.LaptopProcessorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LaptopGPUModelRepository extends JpaRepository<LaptopGPUModel, UUID> {
    List<LaptopGPUModel> findByAttributeValue(AttributeValue attributeValue);
}
