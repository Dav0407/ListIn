package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<PublicationImage, UUID> {
    List<PublicationImage> findAllByImageUrlIn(List<String> imageUrls);
}