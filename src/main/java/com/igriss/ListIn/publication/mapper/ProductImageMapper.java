package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageMapper {

    private final PublicationRepository publicationRepository;

    public ProductImage toProductImage(String imageUrl) {
        return ProductImage.builder()
                .imageUrl(imageUrl)//for id see bellow
                .imageName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
                .build();
    }

    public List<ProductImage> toProductImageList(List<String> imageUrls, UUID publicationId) {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new RuntimeException("Publication not found"));
        return imageUrls.stream()
                .map(url -> {
                    ProductImage productImage = toProductImage(url);
                    productImage.setPublication(publication);//id is assigned here
                    return productImage;
                }).toList();
    }
}
