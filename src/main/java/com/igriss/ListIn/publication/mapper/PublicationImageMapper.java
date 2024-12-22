package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationImageMapper {

    private final PublicationRepository publicationRepository;

    public PublicationImage toProductImage(String imageUrl) {
        return PublicationImage.builder()
                .imageUrl(imageUrl)//for id see bellow
                .imageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
                .build();
    }

    public List<PublicationImage> toProductImageList(List<String> imageUrls, UUID publicationId) {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new RuntimeException("Publication not found"));
        return imageUrls.stream()
                .map(url -> {
                    PublicationImage publicationImage = toProductImage(url);
                    publicationImage.setPublication(publication);//id is assigned here
                    return publicationImage;
                }).toList();
    }
}
