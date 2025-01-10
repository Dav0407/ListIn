package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.ValidationException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.user_publications.AttributeDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.entity.*;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.AttributeKeyDocument;
import com.igriss.ListIn.search.entity.AttributeValueDocument;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.mapper.PublicationDocumentMapper;
import com.igriss.ListIn.search.repository.PublicationDocumentRepository;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private final PublicationAttributeValueRepository publicationAttributeValueRepository;
    private final PublicationDocumentRepository publicationDocumentRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final PublicationRepository publicationRepository;

    private final ProductFileService productFileService;
    private final UserService userService;

    private final PublicationDocumentMapper publicationDocumentMapper;
    private final PublicationImageMapper publicationImageMapper;
    private final PublicationMapper publicationMapper;


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
    public PageResponse<UserPublicationDTO> findAllByUser(int page, int size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllBySeller(pageable, user);

        // Convert publications to DTOs and populate fields
        List<UserPublicationDTO> publicationsDTO = publicationPage.stream()
                .map(publication -> {
                    // Map publication to UserPublicationDTO
                    UserPublicationDTO userPublicationDTO = publicationMapper.toUserPublicationDTO(publication);

                    userPublicationDTO.setProductImages(
                            publicationImageMapper.toImageDTOList(
                                    productFileService.findImagesByPublicationId(publication.getId())
                            )
                    );

                    userPublicationDTO.setVideoUrl(
                            productFileService.findVideoUrlByPublicationId(publication.getId())
                    );

                    List<PublicationAttributeValue> pavList = publicationAttributeValueRepository.findByPublication_Id(publication.getId());
                    List<AttributeDTO> attributes = pavList.stream()
                            .map(pav -> AttributeDTO.builder()
                                    .key(pav.getCategoryAttribute().getAttributeKey().getName())
                                    .value(pav.getAttributeValue().getValue())
                                    .build()
                            ).toList();
                    userPublicationDTO.setAttributes(attributes);

                    return userPublicationDTO;
                }).toList();

        return new PageResponse<>(
                publicationsDTO,
                publicationPage.getNumber(),
                publicationPage.getSize(),
                publicationPage.getTotalElements(),
                publicationPage.getTotalPages(),
                publicationPage.isFirst(),
                publicationPage.isLast()
        );
    }


    private void saveIntoPublicationDocument(Publication publication, List<PublicationAttributeValue> pavList) {

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
                            .attributeValue(valueDocuments)
                            .build();
                })
                .collect(Collectors.toList());

        // Create a PublicationDocument and set attributeKeyDocuments
        PublicationDocument publicationDocument = publicationDocumentMapper.toPublicationDocument(publication, attributeKeyDocuments);

        publicationDocumentRepository.save(publicationDocument);
    }


    private void savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication) {

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

                pavList.add(publicationAttributeValueRepository.save(pav));
            }
        }

        //map into elastic search engine and save publication document
        saveIntoPublicationDocument(publication, pavList);
    }
}
