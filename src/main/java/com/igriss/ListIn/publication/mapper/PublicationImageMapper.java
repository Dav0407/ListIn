package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.dto.ImageDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationImageMapper {

    private final PublicationRepository publicationRepository;

    public PublicationImage toProductImage(String imageUrl, Publication publication) {
        return PublicationImage.builder()
                .imageUrl(imageUrl)
                .publication(publication)
                .imageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
                .build();
    }

    public List<ImageDTO> toImageDTOList(List<PublicationImage> publicationImages) {
        return publicationImages.stream().map(
                this::toImageDTO
        ).toList();
    }

    private ImageDTO toImageDTO(PublicationImage publicationImage) {
        return ImageDTO.builder()
                .isPrimary(publicationImage.getIsPrimaryImage())
                .url(publicationImage.getImageUrl())
                .build();
    }
}
