package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageMapper {

    public ProductImage toProductImage(String imageUrl) {
        return ProductImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public List<ProductImage> toProductImageList(List<String> imageUrls, Publication publication) {
        return imageUrls.stream()
                .map(url -> {
                    ProductImage productImage = toProductImage(url);
                    productImage.setPublication(publication);
                    return productImage;
                }).toList();
    }
}
