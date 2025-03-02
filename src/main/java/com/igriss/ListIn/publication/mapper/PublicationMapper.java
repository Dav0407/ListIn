package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationStatus;
import com.igriss.ListIn.publication.enums.PublicationType;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

//todo 1 -> write an implementation for advanced mapping from PublicationRequestDTO <-> Publication
//todo 2 -> also for PublicationResponseDTO <- Publication (The entire mapper part will be done by Davron)
@Service
@RequiredArgsConstructor
public class PublicationMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final PublicationImageMapper publicationImageMapper;
    private final PublicationAttributeValueMapper publicationAttributeValueMapper;


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
                .category(categoryMapper.toCategory(requestDTO.getCategoryId()))
                .productCondition(productCondition)
                .likes(0L)
                .views(0L)
                .publicationType(publicationType)
                .publicationStatus(publicationStatus)
                .seller(connectedUser)
                .build();
    }

    public PublicationResponseDTO toPublicationResponseDTO(Publication publication, List<PublicationImage> publicationImages, String publicationVideo, List<NumericValue> numericValues, Boolean liked, Boolean following) {
        return PublicationResponseDTO.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .price(publication.getPrice() != null ? publication.getPrice() : 0F)
                .bargain(publication.getBargain())
                .isLiked(liked)
                .sellerType(publication.getSeller().getRole().name())
                .isFree(publication.getPrice() == null || publication.getPrice() == 0F)
                .productImages(publicationImageMapper.toImageDTOList(publicationImages))
                .videoUrl(publicationVideo)
                .publicationType(publication.getPublicationType())
                .productCondition(publication.getProductCondition())
                .likes(publication.getLikes() != null ? publication.getLikes() : 0L)
                .createdAt(publication.getDatePosted())
                .updatedAt(publication.getDateUpdated())
                .category(categoryMapper.toCategoryResponseDTO(publication.getCategory()))
                .seller(userMapper.toUserResponseDTO(publication.getSeller(), following))
                .attributeValue(publicationAttributeValueMapper.toPublicationAttributeValueDTO(publication, numericValues))
                .build();
    }

    public PublicationNode toPublicationNode(PublicationResponseDTO first, PublicationResponseDTO second) {
        return PublicationNode.builder()
                .isSponsored(first.getVideoUrl() != null)
                .firstPublication(first)
                .secondPublication(second)
                .build();
    }

}

