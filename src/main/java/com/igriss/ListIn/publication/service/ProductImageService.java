package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    void saveImages(List<ProductImage> imageUrls);
}
