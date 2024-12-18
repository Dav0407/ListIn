package com.igriss.ListIn.publication.repository.brand_models_repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.LaptopProcessorModel;
import com.igriss.ListIn.publication.entity.brand_models.PCBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.UUID;


public interface LaptopProcessorModelRepository extends JpaRepository<LaptopProcessorModel, UUID> {
    List<LaptopProcessorModel> findByAttributeValue(AttributeValue attributeValue);

}
