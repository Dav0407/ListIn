package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.exceptions.*;
import com.igriss.ListIn.publication.dto.NumericValueRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.entity.*;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.*;
import com.igriss.ListIn.search.service.PublicationDocumentService;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    private final PublicationLikeRepository publicationLikeRepository;
    private final PublicationRepository publicationRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final ProductFileService productFileService;
    private final UserService userService;

    private final PublicationDocumentService publicationDocumentService;
    private final PublicationMapper publicationMapper;

    private final NumericValueRepository numericValueRepository;
    private final NumericFieldRepository numericFieldRepository;
    private final PublicationAttributeValueService publicationAttributeValueService;
    private final PublicationViewRepository publicationViewRepository;

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

        List<NumericValue> numericValues = savePublicationNumericValues(request.getNumericValues(), publication);

        // Save attribute values
        savePublicationAttributeValues(request.getAttributeValues(), publication, numericValues);

        return publication.getId();
    }

    @Override
    public PageResponse<UserPublicationDTO> findAllByUser(int page, int size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllBySeller(pageable, user);

        List<UserPublicationDTO> publicationsDTOList = publicationPage.stream()

                .map(publication ->

                        publicationMapper.toUserPublicationDTO(publication,

                                productImageRepository.findAllByPublication_Id(publication.getId()),

                                productFileService.findVideoUrlByPublicationId(publication.getId()),

                                numericValueRepository.findAllByPublication_Id(publication.getId())
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
    public PageResponse<PublicationResponseDTO> findPublicationsContainingVideo(int page, int size, Authentication connectedUser, UUID pCategory) {
        Page<PublicationVideo> publicationVideos;

        if (pCategory != null)
            publicationVideos = productVideoRepository.findAllByPublication_Category_ParentCategory_IdOrderByPublication_DateUpdatedDesc(pCategory, PageRequest.of(page, size));
        else
            publicationVideos = productVideoRepository.findAllByOrderByPublication_DateUpdatedDesc(PageRequest.of(page, size));

        Page<Publication> publicationPage = publicationVideos.map(
                publicationVideo -> publicationRepository.findById(
                                publicationVideo.getPublication().getId())
                        .orElseThrow(() -> new PublicationNotFoundException(
                                String.format("Publication with id '%s' doesn't exist", publicationVideo.getPublication().getId())))
        );

        User user = (User) connectedUser.getPrincipal();

        List<PublicationResponseDTO> responseDTOList = getPublicationResponseDTOS(publicationPage, user);

        return getPageResponse(publicationPage, responseDTOList);
    }

    @Override
    @Transactional
    public UUID likePublication(UUID publicationId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException("No such Publication found"));

        if (publicationLikeRepository.existsByUserAndPublication(user, publication)) {
            log.error("User have already liked to publication with id: {}", publication.getId());
            throw new BadRequestException(String.format("You have already liked to publication with id: %s", publication.getId()));
        }

        Integer isUpdated = publicationRepository.incrementLike(publication.getId());

        if (isUpdated != 0)
            log.info("Publication with ID: '{}' UPDATED", publicationId);
        else
            log.warn("Failed to UPDATE publication with ID: '{}'", publicationId);

        publicationLikeRepository.save(PublicationLike.builder()
                .user(user)
                .publication(publication)
                .build());

        return publication.getId();
    }

    @Override
    @Transactional
    public UUID unLikePublication(UUID publicationId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        if (!publicationRepository.existsById(publicationId)) {
            throw new PublicationNotFoundException("No such Publication found");
        }

        if (!publicationLikeRepository.existsByUserAndPublication_Id(user, publicationId)) {
            log.error("User have not liked to this publication before: {}", publicationId);
            throw new BadRequestException(String.format("You have not liked to this publication before: %s", publicationId));
        }

        Integer isUpdated = publicationRepository.decrementLike(publicationId);

        if (isUpdated != 0)
            log.info("Publication with ID: '{}' UNLIKED", publicationId);
        else
            log.warn("Failed to UNLIKE publication with ID: '{}'", publicationId);


        publicationLikeRepository.findByPublication_IdAndUser_UserId(publicationId, user.getUserId())
                .ifPresent(publicationLike -> publicationLikeRepository.deleteById(publicationLike.getId()));

        return publicationId;
    }

    @Override
    @Transactional
    public UUID viewPublication(UUID publicationId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        if (!publicationRepository.existsById(publicationId)) {
            log.error("Publication with id: {} does not exist", publicationId);
        }

        UUID newId = UUID.randomUUID();
        publicationViewRepository.upsertView(newId, publicationId, user.getUserId());
        return publicationId;
    }

    @Override
    public PageResponse<PublicationResponseDTO> findAllLikedPublications(Integer page, Integer size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Page<Publication> publicationPage = publicationLikeRepository.findAllByUser(user, PageRequest.of(page, size))
                .map(publicationLike ->
                        publicationRepository.findById(publicationLike.getPublication().getId()).orElseThrow(() -> new PublicationNotFoundException(
                                String.format("Publication with ID '%s' not found", publicationLike.getPublication().getId()))
                        )

                );

        List<PublicationResponseDTO> publicationResponseDTOS = publicationPage.stream()
                .map(publication -> {
                            PublicationResponseDTO publicationResponseDTO = publicationMapper.toPublicationResponseDTO(publication,
                                    productFileService.findImagesByPublicationId(publication.getId()),
                                    productFileService.findVideoUrlByPublicationId(publication.getId()),
                                    numericValueRepository.findAllByPublication_Id(publication.getId()),
                                    true, userService.isFollowingToUser(user, publication.getSeller()));
                            publicationResponseDTO.setIsViewed(isViewed(user, publication));
                            return publicationResponseDTO;
                        }
                ).toList();

        return getPageResponse(publicationPage, publicationResponseDTOS);
    }


    @Override
    public PageResponse<PublicationResponseDTO> findAllByUserId(UUID userId, Integer page, Integer size, Authentication connectedUser) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllBySeller_UserId(userId, pageable);

        User user = (User) connectedUser.getPrincipal();

        List<PublicationResponseDTO> publicationResponseDTOS = getPublicationResponseDTOS(publicationPage, user);

        return getPageResponse(publicationPage, publicationResponseDTOS);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PublicationResponseDTO updateUserPublication(UUID publicationId, UpdatePublicationRequestDTO updatePublication, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        if (!publication.getSeller().getUserId().equals(connectedUser.getUserId())) {
            log.warn("User with ID: '{}' trying to update publication with id: '{}' which belongs to user with ID: '{}'",
                    connectedUser.getUserId(), publicationId, publication.getSeller().getUserId());
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

        if (!(updatePublication.getImageUrls() == null || updatePublication.getImageUrls().isEmpty()))
            productFileService.updateImagesByPublication(publication, updatePublication.getImageUrls());


        if (!(updatePublication.getVideoUrl() == null || updatePublication.getVideoUrl().isEmpty()))
            productFileService.updateVideoByPublication(publication, updatePublication.getVideoUrl());

        publicationDocumentService.updateInPublicationDocument(publicationId, updatePublication);


        Publication updatedPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(String.format("Publication with id [%s] does not exist!", publicationId)));

        List<PublicationImage> images = productFileService.findImagesByPublicationId(updatedPublication.getId());

        String videoUrl = productFileService.findVideoUrlByPublicationId(updatedPublication.getId());

        PublicationResponseDTO publicationResponseDTO = publicationMapper.toPublicationResponseDTO(
                updatedPublication, images, videoUrl, numericValueRepository.findAllByPublication_Id(publication.getId()),
                false, userService.isFollowingToUser(connectedUser, publication.getSeller())
        );
        publicationResponseDTO.setIsViewed(isViewed(connectedUser, publication));
        return publicationResponseDTO;
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deletePublication(UUID publicationId, Authentication authentication) {

        return publicationRepository.findById(publicationId).map(publication -> {

            publicationRepository.deleteById(publicationId);

            publicationAttributeValueService.deletePublicationAttributes(publicationId);

            productFileService.deletePublicationFiles(publicationId);

            publicationDocumentService.deleteById(publicationId);

            return ResponseEntity.noContent().build();

        }).orElse(ResponseEntity.notFound().build());

    }


    private void savePublicationAttributeValues(List<PublicationRequestDTO.AttributeValueDTO> attributeValues, Publication publication, List<NumericValue> numericValues) {

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
        publicationDocumentService.saveIntoPublicationDocument(publication, pavList, numericValues);
    }

    @NotNull
    private PageResponse<PublicationResponseDTO> getPageResponse(Page<Publication> publicationPage, List<PublicationResponseDTO> publicationsDTOList) {
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

    @NotNull
    private List<PublicationResponseDTO> getPublicationResponseDTOS(Page<Publication> publicationPage, User user) {
        return publicationPage.stream()
                .map(publication -> {
                            PublicationResponseDTO publicationResponseDTO = publicationMapper.toPublicationResponseDTO(publication,
                                    productFileService.findImagesByPublicationId(publication.getId()),
                                    productFileService.findVideoUrlByPublicationId(publication.getId()),
                                    numericValueRepository.findAllByPublication_Id(publication.getId()),
                                    isLiked(user, publication),
                                    userService.isFollowingToUser(user, publication.getSeller()));
                            publicationResponseDTO.setIsViewed(isViewed(user, publication));
                            return publicationResponseDTO;
                        }
                ).toList();

    }

    private Boolean isLiked(User user, Publication publication) {
        return publicationLikeRepository.existsByUserAndPublication(user, publication);
    }

    private Boolean isViewed(User user, Publication publication) {
        return publicationViewRepository.existsByPublicationAndUser(publication, user);
    }

    private List<NumericValue> savePublicationNumericValues(List<NumericValueRequestDTO> request, Publication publication) {
        if (request != null && !request.isEmpty()) {
            List<NumericValue> numericValues = request.stream()
                    .map(numericDto -> NumericValue.builder()
                            .publication(publication)
                            .numericField(numericFieldRepository.findById(numericDto.getNumericFieldId()).orElseThrow(() ->
                                    new ResourceNotFoundException(String.format("Numeric filed with ID [%s] does not exist!!!", numericDto.getNumericFieldId()))))
                            .value(numericDto.getNumericValue())
                            .build())
                    .toList();
            return numericValueRepository.saveAll(numericValues);
        }
        return null;
    }
}
