package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;

import java.util.List;
import java.util.UUID;

public interface PublicationAttributeValueService {

    List<PublicationAttributeValue> savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication, List<NumericValue> numericValues);

    void deletePublicationAttributes(UUID publicationId);
}
