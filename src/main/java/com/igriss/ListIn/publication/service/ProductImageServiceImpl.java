package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;

    @Override
    public void saveImages(List<String> imageUrls, Publication publication) {

        List<ProductImage> productImageList = productImageRepository.findAllByImageUrlIn(imageUrls);
        productImageList.forEach(productImage -> productImage.setPublication(publication));

        productImageRepository.saveAll(productImageList);
    }
}
