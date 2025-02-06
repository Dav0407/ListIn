package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.document.AttributeKeyDocument;
import com.igriss.ListIn.search.document.PublicationDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationDocumentMapper {

    public PublicationDocument toPublicationDocument(Publication publication, List<AttributeKeyDocument> attributes, List<PublicationDocument.NumericFieldDocument> document) {
        return PublicationDocument.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .locationName(publication.getLocationName())
                .price(publication.getPrice())
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
