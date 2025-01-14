package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.entity.AttributeKeyDocument;
import com.igriss.ListIn.search.entity.PublicationDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationDocumentMapper {

    public PublicationDocument toPublicationDocument(Publication publication, List<AttributeKeyDocument> attributes) {
        return PublicationDocument.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .locationName(publication.getLocationName())
                .price(publication.getPrice())
                .bargain(publication.getBargain())
                .productCondition(publication.getProductCondition())
                .categoryName(publication.getCategory().getName())
                .categoryDescription(publication.getCategory().getDescription())
                .parentCategoryName(publication.getCategory().getParentCategory().getName())
                .parentCategoryDescription(publication.getCategory().getParentCategory().getDescription())
                .attributeKeys(attributes)
                .build();
    }
}
