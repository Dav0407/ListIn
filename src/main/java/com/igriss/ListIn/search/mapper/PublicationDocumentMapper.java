package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.document.AttributeKeyDocument;
import com.igriss.ListIn.search.document.PublicationDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationDocumentMapper {

    public PublicationDocument toPublicationDocument(Publication publication, List<AttributeKeyDocument> attributes,
                                                     List<PublicationDocument.NumericFieldDocument> document, Map<String, UUID> locationIds) {
        return PublicationDocument.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .locationName(publication.getLocationName())
                .countryId(locationIds.get("countryId"))
                .stateId(locationIds.get("stateId"))
                .countyId(locationIds.get("countyId"))
                .cityId(locationIds.get("cityId"))
                .price(publication.getPrice())
                .sellerType(publication.getSeller().getRole().name())
                .bargain(publication.getBargain())
                .productCondition(publication.getProductCondition())
                .categoryId(publication.getCategory().getId())
                .categoryDescription(publication.getCategory().getDescription())
                .parentCategoryId(publication.getCategory().getParentCategory().getId())
                .parentCategoryDescription(publication.getCategory().getParentCategory().getDescription())
                .attributeKeys(attributes)
                .numericFields(document)
                .build();
    }
}
