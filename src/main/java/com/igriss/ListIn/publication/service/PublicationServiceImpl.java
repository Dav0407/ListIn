package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.ValidationException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final PublicationMapper publicationMapper;
    private final ProductFileService productFileService;
    private final PublicationRepository publicationRepository;
    private final PublicationAttributeValueRepository publicationAttributeValueRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    @Transactional
    @Override
    public UUID savePublication(PublicationRequestDTO request, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        productFileService.saveImages(request.getImageUrls(), publication);
        productFileService.saveVideo(request.getVideoUrl(),publication);

        List<PublicationRequestDTO.AttributeValueDTO> attributeValues = request.getAttributeValues();

        savePublicationAttributeValues(attributeValues, publication);

        return publication.getId();
    }

    private void savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication) {

        List<CategoryAttribute> categoryAttributes = categoryAttributeRepository
                .findByCategory_Id(publication.getCategory().getId());

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
                throw new ValidationException(
                        "This attribute allows only one value",
                        List.of("Attribute " + attributeValueDTO.getAttributeId() + " allows only one value")
                );
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

                publicationAttributeValueRepository.save(pav);
            }
        }
    }
}
