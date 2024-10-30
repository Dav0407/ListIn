package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.PublicationUploadRequestDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

//todo 1 -> write an implementation for advanced mapping from PublicationUploadRequestDTO <-> Publication
//todo 2 -> also for PublicationResponseDTO <- Publication (The entire mapper part will be done by Davron)
@Service
@RequiredArgsConstructor
public class PublicationMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public Publication toPublication(PublicationUploadRequestDTO requestDTO) {
        return Publication.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .stockQuantity(requestDTO.getStockQuantity())
                .categories(requestDTO.getCategories() != null
                        ? requestDTO.getCategories().stream().map(categoryMapper::toCategory).collect(Collectors.toList()) : null)
                //todo -> add mapping (publicationType, publicationStatus, seller)
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
                .categories(publication.getCategories() != null ?
                        publication.getCategories().stream().map(categoryMapper::toCategoryResponseDTO).collect(Collectors.toList()) : null) // todo -> better NullPointerException handling
                .productCondition(publication.getProductCondition() != null ? publication.getProductCondition().getName() : null)
                .publicationType(publication.getPublicationType() != null ? publication.getPublicationType().getName() : null)
                .publicationStatus(publication.getPublicationStatus() != null ? publication.getPublicationStatus().getName() : null)
                .seller(userMapper.toUserResponseDTO(publication.getSeller()))
                .build();
    }

}

