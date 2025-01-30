package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.PublicationNotFoundException;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.UnauthorizedAccessException;
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
import com.igriss.ListIn.search.dto.PublicationNode;
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

import java.util.*;

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

    private final PublicationNodeHandler publicationNodeHandler1;
    private final PublicationNodeHandler publicationNodeHandler2;


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
    public List<PublicationNode> findAllLatestPublications(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllByOrderByDatePostedDesc(pageable);

        return publicationNodeHandler1.handlePublicationNodes(publicationPage
                .getContent()
                .stream()
                .map(publication -> publicationMapper.toPublicationResponseDTO(
                        publication,
                        productImageRepository.findAllByPublication_Id(publication.getId()),
                        productVideoRepository.findByPublication_Id(publication.getId())
                                .map(PublicationVideo::getVideoUrl)
                                .orElse(null)
                ))
                .toList(), publicationPage.isLast());
    }


    @Override
    public List<PublicationNode> findWithParentCategory(UUID parentCategoryId, Integer page, Integer size) {

        Page<Publication> publicationPage = publicationRepository.findAllByCategory_ParentCategory_Id(parentCategoryId, PageRequest.of(page, size, Sort.by("datePosted").descending()));

        List<PublicationResponseDTO> publications =
                publicationPage
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
        return publicationNodeHandler2.handlePublicationNodes(publications, publicationPage.isLast());
    }

    @Override
    public PageResponse<PublicationResponseDTO> findPublicationsContainingVideo(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PublicationVideo> publicationVideos = productVideoRepository.findAllByOrderByPublication_DateUpdatedDesc(pageable);

        List<Publication> publications = publicationVideos.getContent().stream().map(
                publicationVideo -> publicationRepository.findById(
                                publicationVideo.getPublication().getId())
                        .orElseThrow(() -> new PublicationNotFoundException(
                                String.format("Publication with id '%s' doesn't exist", publicationVideo.getPublication().getId())))
        ).toList();

        return new PageResponse<>(
                publications.stream()
                        .map(publication ->
                                publicationMapper.toPublicationResponseDTO(publication,
                                        productFileService.findImagesByPublicationId(publication.getId()),
                                        productFileService.findVideoUrlByPublicationId(publication.getId())
                                )
                        ).toList(),
                publicationVideos.getNumber(),
                publicationVideos.getSize(),
                publicationVideos.getTotalElements(),
                publicationVideos.getTotalPages(),
                publicationVideos.isFirst(),
                publicationVideos.isLast()
        );
    }

    @Override
    @Transactional
    public UUID likePublication(UUID publicationId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PublicationNotFoundException("No such Publication found"));

        Integer isUpdated = publicationRepository.incrementLike(publication.getId());

        if (isUpdated != 0)
            log.info("Publication with ID: '{}' UPDATED",publicationId);
        else
            log.warn("Failed to UPDATE publication with ID: '{}'", publicationId);


        return publication.getId();
    }

    @Override
    public PageResponse<PublicationResponseDTO> findAllByUserId(UUID userId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllBySeller_UserId(userId, pageable);

        List<PublicationResponseDTO> publicationsDTOList = publicationPage.stream()
                .map(publication ->
                        publicationMapper.toPublicationResponseDTO(publication,
                                productFileService.findImagesByPublicationId(publication.getId()),
                                productFileService.findVideoUrlByPublicationId(publication.getId())
                        )
                ).toList();

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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PublicationResponseDTO updateUserPublication(UUID publicationId, UpdatePublicationRequestDTO updatePublication, Authentication authentication) {

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        if (!publication.getSeller().getUserId().equals(((User) authentication.getPrincipal()).getUserId())) {
            log.warn("User with ID: '{}' trying to update publication with id: '{}' which belongs to user with ID: '{}'",
                    ((User) authentication.getPrincipal()).getUserId(), publicationId, publication.getSeller().getUserId());
            throw new UnauthorizedAccessException(String.format("Permission denied: you do not have access to update publication with ID %s", publicationId));
        }

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
        if (updatePublication.getImageUrls() != null)
            productFileService.updateImagesByPublication(
                    publication,
                    updatePublication.getImageUrls()
            );
        if (updatePublication.getVideoUrl() != null)
            productFileService.updateVideoByPublication(
                    publication,
                    updatePublication.getVideoUrl()
            );
        publicationDocumentService.updateInPublicationDocument(publicationId, updatePublication);


        Publication updatedPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        List<PublicationImage> images = productFileService.findImagesByPublicationId(updatedPublication.getId());

        String videoUrl = productFileService.findVideoUrlByPublicationId(updatedPublication.getId());

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
