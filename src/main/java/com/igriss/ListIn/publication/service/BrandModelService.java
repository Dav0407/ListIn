package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.BrandModelDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;

import java.util.List;

public interface BrandModelService {
    List<BrandModelDTO> getCorrespondingModels(AttributeKey attributeKey, AttributeValue attributeValue);
    List<BrandModelDTO> getModels(AttributeValue attributeValue);
}
