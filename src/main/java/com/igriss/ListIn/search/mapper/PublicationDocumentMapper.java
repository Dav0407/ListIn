package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.document.AttributeKeyDocument;
import com.igriss.ListIn.search.document.PublicationDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PublicationDocumentMapper {

    public PublicationDocument toPublicationDocument(Publication publication, List<AttributeKeyDocument> attributes,
                                                     List<PublicationDocument.NumericFieldDocument> document) {
        return PublicationDocument.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .locationName(safeGet(publication::getLocationName))
                .countryId(safeGet(() -> publication.getCountry().getId()))
                .stateId(safeGet(() -> publication.getState().getId()))
                .countyId(safeGet(() -> publication.getCounty().getId()))
                //.cityId(safeGet(() -> publication.getSeller().getCityId())) // TODO: Marked for removal
                .price(publication.getPrice())
                .sellerType(safeGet(() -> publication.getSeller().getRole().name()))
                .bargain(publication.getBargain())
                .productCondition(publication.getProductCondition())
                .categoryId(safeGet(() -> publication.getCategory().getId()))
                .categoryDescription(safeGet(() -> publication.getCategory().getDescription()))
                .parentCategoryId(safeGet(() -> publication.getCategory().getParentCategory().getId()))
                .parentCategoryDescription(safeGet(() -> publication.getCategory().getParentCategory().getDescription()))
                .attributeKeys(attributes)
                .numericFields(document)
                .build();
    }

    /**
     * Utility method to safely retrieve values and return null if an exception occurs.
     */
    private <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
