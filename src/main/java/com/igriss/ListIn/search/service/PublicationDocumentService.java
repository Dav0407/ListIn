package com.igriss.ListIn.search.service;

import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PublicationDocumentService {
    void saveIntoPublicationDocument(Publication publication, List<PublicationAttributeValue> pavList, List<NumericValue> numericValues, Map<String, UUID> locationIds);

    void updateInPublicationDocument(UUID publicationId, UpdatePublicationRequestDTO updatePublication);

    void updateInPublicationDocumentAttributes();

    void deleteById(UUID publicationId);
}
