package com.igriss.ListIn.publication.repository.brand_models_repository;

import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.LaptopBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LaptopBrandModelRepository extends JpaRepository<LaptopBrandModel, UUID> {
  List<LaptopBrandModel> findByAttributeValue(AttributeValue attributeValue);
}