package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.ValidationException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.mapper.PublicationDocumentMapper;
import com.igriss.ListIn.search.repository.PublicationDocumentRepository;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    private final UserService userService;

    private final PublicationDocumentMapper publicationDocumentMapper;
    private final PublicationDocumentRepository publicationDocumentRepository;
    private final PublicationImageMapper publicationImageMapper;


    @Transactional
    @Override
    public UUID savePublication(PublicationRequestDTO request, Authentication authentication) {
        // Extract user from authentication
        User connectedUser = (User) authentication.getPrincipal();

        userService.updateContactDetails(request, connectedUser);

        // Map and save publication
        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        // Save images
        productFileService.saveImages(request.getImageUrls(), publication);

        //map into elastic search engine and save publication document
        saveIntoPublicationDocument(publication);

        // Save video if present
        Publication finalPublication = publication;
        Optional.ofNullable(request.getVideoUrl())
                .filter(url -> !url.isEmpty())
                .ifPresent(url -> productFileService.saveVideo(url, finalPublication));

        // Save attribute values
        savePublicationAttributeValues(request.getAttributeValues(), publication);

        return publication.getId();
    }

    @Override
    public List<PublicationResponseDTO> findAllByUser(Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();

        List<Publication> publications = publicationRepository.findAllBySeller(user);

        return publications.stream()
                .map(publicationMapper::toPublicationResponseDTO)
                .peek(
                        publicationResponseDTO -> publicationResponseDTO.setProductImages(
                                publicationImageMapper.toImageDTOList(
                                        productFileService.findImagesByPublicationId(publicationResponseDTO.getId())
                                ))
                )
                .peek(
                        publicationResponseDTO -> publicationResponseDTO.setVideoUrl(
                                productFileService.findVideoUrlByPublicationId(publicationResponseDTO.getId())
                        )
                )
                .toList();
    }

    private void saveIntoPublicationDocument(Publication publication) {
        PublicationDocument publicationDocument = publicationDocumentMapper.toPublicationDocument(publication);
        publicationDocumentRepository.save(publicationDocument);

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
