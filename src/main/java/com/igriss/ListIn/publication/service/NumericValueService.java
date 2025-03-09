package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.NumericValueRequestDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;

import java.util.List;
import java.util.UUID;

public interface NumericValueService {
    List<NumericValue> savePublicationNumericValues(List<NumericValueRequestDTO> request, Publication publication);

    void deletePublicationNumericFields(UUID publicationId);

    List<NumericValue> findNumericFields(UUID id);
}
