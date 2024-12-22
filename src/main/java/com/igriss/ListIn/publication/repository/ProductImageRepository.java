package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<PublicationImage, Long> {
    List<PublicationImage> findAllByImageUrlIn(List<String> imageUrls);
}