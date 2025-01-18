package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.PublicationNotFoundException;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.ValidationException;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.dto.user_publications.AttributeDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.entity.*;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.*;
import com.igriss.ListIn.search.service.PublicationDocumentService;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private final PublicationAttributeValueRepository publicationAttributeValueRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final PublicationRepository publicationRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final ProductFileService productFileService;
    private final UserService userService;

    private final PublicationDocumentService publicationDocumentService;
    private final PublicationImageMapper publicationImageMapper;
    private final PublicationMapper publicationMapper;

    @Override
    @Transactional
    public UUID savePublication(PublicationRequestDTO request, Authentication authentication) {
        // Extract user from authentication
        User connectedUser = (User) authentication.getPrincipal();

        userService.updateContactDetails(request, connectedUser);

        // Map and save publication
        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        // Save images //todo -> then removed the assignment

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
        List<UserPublicationDTO> publicationsDTOList = publicationPage.stream()
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
                publicationsDTOList,
                publicationPage.getNumber(),
                publicationPage.getSize(),
                publicationPage.getTotalElements(),
                publicationPage.getTotalPages(),
                publicationPage.isFirst(),
                publicationPage.isLast()
        );
    }

    @Override
    public PageResponse<PublicationResponseDTO> findAllLatestPublications(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository
                .findAllByOrderByDatePostedDesc(pageable);

        List<PublicationResponseDTO> publicationResponseDTOList = publicationPage
                .getContent()
                .stream()
                .map(publication ->
                        publicationMapper
                                .toPublicationResponseDTO(publication,
                                        productImageRepository
                                                .findAllByPublication_Id(publication.getId()),
                                        productVideoRepository
                                                .findByPublication_Id(publication.getId())
                                                .map(PublicationVideo::getVideoUrl)
                                                .orElse(null))
                )
                .toList();

        return new PageResponse<>(
                publicationResponseDTOList,
                publicationPage.getNumber(),
                publicationPage.getSize(),
                publicationPage.getTotalElements(),
                publicationPage.getTotalPages(),
                publicationPage.isFirst(),
                publicationPage.isLast()
        );
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PublicationResponseDTO updateUserPublication(UUID publicationId, UpdatePublicationRequestDTO updatePublication) {

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        Integer isUpdatedPublication = publicationRepository.updatePublicationById(
                publicationId,
                updatePublication.getTitle(),
                updatePublication.getDescription(),
                updatePublication.getPrice(),
                updatePublication.getBargain(),
                updatePublication.getProductCondition()
        );

        if (isUpdatedPublication != 0) {
            log.info("Publication updated: {}", publication);
        } else {
            log.info("Publication update failed: {}", publication);
        }

        productFileService.updateImagesByPublication(
                publication,
                updatePublication.getImageUrls()
        );
        productFileService.updateVideoByPublication(
                publication,
                updatePublication.getVideoUrl()
        );

        Publication updatedPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        List<PublicationImage> images = productFileService.findImagesByPublicationId(updatedPublication.getId());

        String videoUrl = productFileService.findVideoUrlByPublicationId(updatedPublication.getId());

        publicationDocumentService.updateInPublicationDocument(publicationId, updatePublication);

        return publicationMapper.toPublicationResponseDTO(updatedPublication, images, videoUrl);
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

        //map into elastic search engine and save publication document
        publicationDocumentService.saveIntoPublicationDocument(publication, pavList);
    }
}
