package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.entity.PublicationDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicationDocumentMapper {

    public PublicationDocument toPublicationDocument(Publication publication) {
        return PublicationDocument.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .locationName(publication.getLocationName())
                .productCondition(publication.getProductCondition())
                .categoryName(publication.getCategory().getName())
                .categoryDescription(publication.getCategory().getDescription())
                .parentCategoryName(publication.getCategory().getParentCategory().getName())
                .parentCategoryDescription(publication.getCategory().getParentCategory().getDescription())
                .build();
    }
}
