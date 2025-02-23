package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;

import java.util.List;
import java.util.UUID;

public interface PublicationAttributeValueService {

    void savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication, List<NumericValue> numericValues);

    void deletePublicationAttributes(UUID publicationId);
}
