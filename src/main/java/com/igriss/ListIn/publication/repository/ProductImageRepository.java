package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findAllByImageUrlIn(List<String> imageUrls);
}