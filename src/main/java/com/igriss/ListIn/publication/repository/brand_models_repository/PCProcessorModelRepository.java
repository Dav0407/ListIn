package com.igriss.ListIn.publication.repository.brand_models_repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.PCProcessorModel;
import com.igriss.ListIn.publication.entity.brand_models.SmartphoneBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PCProcessorModelRepository extends JpaRepository<PCProcessorModel, UUID> {
    List<PCProcessorModel> findByAttributeValue(AttributeValue attributeValue);

}
