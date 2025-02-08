package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface ProductImageRepository extends JpaRepository<PublicationImage, UUID> {
    List<PublicationImage> findAllByImageUrlIn(List<String> imageUrls);

    List<PublicationImage> findAllByPublication_Id(UUID publicationId);

    List<PublicationImage> deleteAllByPublication_Id(UUID publicationId);

    void deleteAllByPublication_IdAndImageUrlIn(UUID id, List<String> removedImageUrls);

    List<PublicationImage> findAllByPublication_IdIn(Collection<UUID> publicationIds);
}