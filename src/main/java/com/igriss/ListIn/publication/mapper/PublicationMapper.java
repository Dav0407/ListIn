package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.Publication;
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
    private final PublicationTypeMapper publicationTypeMapper;
    private final PublicationStatusMapper publicationStatusMapper;
    private final ProductConditionMapper productConditionMapper;

    public Publication toPublication(PublicationRequestDTO requestDTO, User connectedUser) {

        return Publication.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .stockQuantity(requestDTO.getStockQuantity())
                .category(categoryMapper.toCategory(requestDTO.getCategories()))
                .productCondition(productConditionMapper.toProductCondition(requestDTO.getProductCondition()))
                .publicationType(publicationTypeMapper.toPublicationType(requestDTO.getPublicationType()))
                .publicationStatus(publicationStatusMapper.toPublicationStatus(requestDTO.getPublicationStatus()))
                .seller(connectedUser)
                .build();
    }

    public PublicationResponseDTO toPublicationResponseDTO(Publication publication) {
        return PublicationResponseDTO.builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .price(publication.getPrice())
                .stockQuantity(publication.getStockQuantity())
                .createdAt(publication.getCreatedAt())
                .updatedAt(publication.getUpdatedAt())
                .category(categoryMapper.toCategoryResponseDTO(publication.getCategory())) // todo -> better NullPointerException handling
                .productCondition(publication.getProductCondition() != null ? publication.getProductCondition().getName() : null)
                .publicationType(publication.getPublicationType() != null ? publication.getPublicationType().getName() : null)
                .publicationStatus(publication.getPublicationStatus() != null ? publication.getPublicationStatus().getName() : null)
                .seller(userMapper.toUserResponseDTO(publication.getSeller()))
                .build();
    }


}

