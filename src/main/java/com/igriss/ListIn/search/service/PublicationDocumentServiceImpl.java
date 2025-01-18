package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.PublicationNotFoundException;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.search.document.AttributeKeyDocument;
import com.igriss.ListIn.search.document.AttributeValueDocument;
import com.igriss.ListIn.search.document.PublicationDocument;
import com.igriss.ListIn.search.mapper.PublicationDocumentMapper;
import com.igriss.ListIn.search.repository.PublicationDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicationDocumentServiceImpl implements PublicationDocumentService {

    private final PublicationDocumentMapper publicationDocumentMapper;
    private final PublicationDocumentRepository publicationDocumentRepository;


    @Override
    public void saveIntoPublicationDocument(Publication publication, List<PublicationAttributeValue> pavList) {

        List<AttributeValue> attributeValues = pavList.stream()
                .map(PublicationAttributeValue::getAttributeValue)
                .toList();

        // Organize attributeValues into a Map<AttributeKey, List<AttributeValue>>
        Map<AttributeKey, List<AttributeValue>> attributeMap = attributeValues.stream()
                .filter(Objects::nonNull) // Ensure no null values
                .filter(attributeValue -> attributeValue.getAttributeKey() != null) // Ensure attributeKey is not null
                .collect(Collectors.groupingBy(AttributeValue::getAttributeKey));

        // Convert the map into a list of AttributeKeyDocument with corresponding AttributeValueDocument
        List<AttributeKeyDocument> attributeKeyDocuments = attributeMap.entrySet().stream()
                .map(entry -> {
                    // Convert AttributeValue to AttributeValueDocument
                    List<AttributeValueDocument> valueDocuments = entry.getValue().stream()
                            .map(value -> AttributeValueDocument.builder()
                                    .id(value.getId())
                                    .value(value.getValue())
                                    .build())
                            .collect(Collectors.toList());

                    // Create AttributeKeyDocument with AttributeValueDocuments
                    return AttributeKeyDocument.builder()
                            .id(entry.getKey().getId())
                            .key(entry.getKey().getName())
                            .attributeValues(valueDocuments)
                            .build();
                })
                .collect(Collectors.toList());

        // Create a PublicationDocument and set attributeKeyDocuments
        PublicationDocument publicationDocument = publicationDocumentMapper.toPublicationDocument(publication, attributeKeyDocuments);

        publicationDocumentRepository.save(publicationDocument);
    }

    @Override
    public void updateInPublicationDocument(UUID publicationId, UpdatePublicationRequestDTO updatingPublication) {
        PublicationDocument updatedPublication = publicationDocumentRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException("Publication not found with id: " + publicationId));

        if (updatingPublication.getTitle() != null)
            updatedPublication.setTitle(updatingPublication.getTitle());

        if (updatingPublication.getDescription() != null)
            updatedPublication.setDescription(updatingPublication.getDescription());

        if (updatingPublication.getPrice() != null)
            updatedPublication.setPrice(updatingPublication.getPrice());

        if (updatingPublication.getBargain() != null)
            updatedPublication.setBargain(updatingPublication.getBargain());

        if (updatingPublication.getProductCondition() != null)
            updatedPublication.setProductCondition(ProductCondition
                    .valueOf(updatingPublication.getProductCondition()));

        publicationDocumentRepository.save(updatedPublication);
    }


}
