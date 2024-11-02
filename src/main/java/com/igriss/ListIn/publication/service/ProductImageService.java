package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;

import java.util.List;

public interface ProductImageService {
    void saveImages(List<String> imageUrls, Publication publication);
}
