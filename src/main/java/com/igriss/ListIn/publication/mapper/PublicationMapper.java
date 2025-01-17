package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationStatus;
import com.igriss.ListIn.publication.enums.PublicationType;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

//todo 1 -> write an implementation for advanced mapping from PublicationRequestDTO <-> Publication
//todo 2 -> also for PublicationResponseDTO <- Publication (The entire mapper part will be done by Davron)
@Service
@RequiredArgsConstructor
public class PublicationMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final PublicationImageMapper publicationImageMapper;
    private final PublicationVideoMapper publicationVideoMapper;
    public Publication toPublication(PublicationRequestDTO requestDTO, User connectedUser) {

        PublicationType publicationType = switch (connectedUser.getRole()) {
            case BUSINESS_SELLER -> PublicationType.BUSINESS_PUBLICATION;
            case ADMIN -> PublicationType.ADVERTISEMENT_PUBLICATION;
            default -> PublicationType.INDIVIDUAL_PUBLICATION;
        };

        ProductCondition productCondition = ProductCondition.valueOf(requestDTO.getProductCondition());

        PublicationStatus publicationStatus = PublicationStatus.PENDING_APPROVAL;

        return Publication.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .bargain(requestDTO.getBargain())
                .locationName(requestDTO.getLocationName())
                .latitude(requestDTO.getLatitude())
                .longitude(requestDTO.getLongitude())
                .category(categoryMapper.toCategory(requestDTO.getCategoryId()))
                .productCondition(productCondition)
                .publicationType(publicationType)
                .publicationStatus(publicationStatus)
                .seller(connectedUser)
                .build();
    }

    public PublicationResponseDTO toPublicationResponseDTO(Publication publication, List<PublicationImage> publicationImages, String publicationVideo) {
        return PublicationResponseDTO.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .price(publication.getPrice())
                .bargain(publication.getBargain())
                .productImages(publicationImageMapper.toImageDTOList(publicationImages))
                .videoUrl(publicationVideo)
                .locationName(publication.getLocationName())
                .latitude(publication.getLatitude())
                .longitude(publication.getLongitude())
                .publicationType(publication.getPublicationType())
                .productCondition(publication.getProductCondition())
                .createdAt(publication.getDatePosted())
                .updatedAt(publication.getDateUpdated())
                .category(categoryMapper.toCategoryResponseDTO(publication.getCategory()))
                .seller(userMapper.toUserResponseDTO(publication.getSeller()))
                .build();
    }

    public UserPublicationDTO toUserPublicationDTO(Publication publication) {
        return UserPublicationDTO.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .price(publication.getPrice())
                .bargain(publication.getBargain())
                .locationName(publication.getLocationName())
                .latitude(publication.getLatitude())
                .longitude(publication.getLongitude())
                .publicationType(publication.getPublicationType())
                .productCondition(publication.getProductCondition())
                .createdAt(publication.getDatePosted())
                .updatedAt(publication.getDateUpdated())
                .category(categoryMapper.toCategoryResponseDTO(publication.getCategory()))
                .build();
    }


}

