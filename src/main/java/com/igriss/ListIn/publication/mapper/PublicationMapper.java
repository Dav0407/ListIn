package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationStatus;
import com.igriss.ListIn.publication.enums.PublicationType;
import com.igriss.ListIn.security.roles.Role;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//todo 1 -> write an implementation for advanced mapping from PublicationRequestDTO <-> Publication
//todo 2 -> also for PublicationResponseDTO <- Publication (The entire mapper part will be done by Davron)
@Service
@RequiredArgsConstructor
public class PublicationMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public Publication toPublication(PublicationRequestDTO requestDTO, User connectedUser) {

        PublicationType publicationType = switch (connectedUser.getRole()) {
            case BUSINESS_SELLER -> PublicationType.BUSINESS_PUBLICATION;
            case ADMIN -> PublicationType.ADVERTISEMENT_PUBLICATION;
            default -> PublicationType.INDIVIDUAL_PUBLICATION;
        };

        ProductCondition productCondition = ProductCondition.valueOf(requestDTO.getProductCondition());

        PublicationStatus publicationStatus = PublicationStatus.valueOf(requestDTO.getPublicationStatus());

        return Publication.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .bargain(requestDTO.getBargain())
                .locationName(requestDTO.getLocationName())
                .latitude(requestDTO.getLatitude())
                .longitude(requestDTO.getLongitude())
                .category(categoryMapper.toCategory(requestDTO.getCategories()))
                .productCondition(productCondition)
                .publicationType(publicationType)
                .publicationStatus(publicationStatus)
                .seller(connectedUser)
                .build();
    }

    public PublicationResponseDTO toPublicationResponseDTO(Publication publication) {
        return PublicationResponseDTO.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .price(publication.getPrice())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .category(categoryMapper.toCategoryResponseDTO(publication.getCategory())) // todo -> better NullPointerException handling
                .productCondition(publication.getProductCondition().toString())
                .publicationType(publication.getPublicationType().toString())
                .publicationStatus(publication.getPublicationStatus().toString())
                .seller(userMapper.toUserResponseDTO(publication.getSeller()))
                .build();
    }


}

