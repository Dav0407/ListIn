package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.ValidationException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.*;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.service.PublicationAttributeValueService;
import com.igriss.ListIn.search.service.PublicationDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationAttributeValueServiceImpl implements PublicationAttributeValueService {

    private final CategoryAttributeRepository categoryAttributeRepository;

    private final AttributeValueRepository attributeValueRepository;

    private final PublicationAttributeValueRepository publicationAttributeValueRepository;


    @Override
    public List<PublicationAttributeValue> savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication, List<NumericValue> numericValues) {

        List<CategoryAttribute> categoryAttributes = categoryAttributeRepository
                .findByCategory_Id(publication.getCategory().getId());
        List<PublicationAttributeValue> pavList = new ArrayList<>();
        for (PublicationRequestDTO.AttributeValueDTO attributeValueDTO : attributeValues) {
            CategoryAttribute categoryAttribute = categoryAttributes.stream()
                    .filter(ca -> ca.getAttributeKey().getId().equals(attributeValueDTO.getAttributeId()))
                    .findFirst()
                    .orElseThrow(() -> new ValidationException(
                            "Invalid attribute for category",
                            List.of("Attribute " + attributeValueDTO.getAttributeId() + " is not valid for this category")
                    ));

            String widgetType = categoryAttribute.getAttributeKey().getWidgetType();

            if (("oneSelectable".equals(widgetType) || "colorSelectable".equals(widgetType)) && attributeValueDTO.getAttributeValueIds().size() > 1) {
                var exception = new ValidationException(
                        "This attribute allows only one value",
                        List.of("Attribute " + attributeValueDTO.getAttributeId() + " allows only one value"));
                log.error("Exception occurred: ", exception);
                throw exception;
            }

            for (int i = 0; i < attributeValueDTO.getAttributeValueIds().size(); i++) {
                UUID valueId = attributeValueDTO.getAttributeValueIds().get(i);
                AttributeValue attributeValue = attributeValueRepository.findById(valueId)
                        .orElseThrow(() -> new ResourceNotFoundException("Attribute value not found: " + valueId));

                PublicationAttributeValue pav = PublicationAttributeValue.builder()
                        .publication(publication)
                        .categoryAttribute(categoryAttribute)
                        .attributeValue(attributeValue)
                        .valueOrder("multiSelectable".equals(widgetType) ? i : 0)
                        .build();

                pavList.add(publicationAttributeValueRepository.save(pav));
            }
        }

        return pavList;
    }


    @Override
    public void deletePublicationAttributes(UUID publicationId) {
        publicationAttributeValueRepository.deleteAllByPublication_Id(publicationId);
    }
}
