package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductVideoRepository extends JpaRepository<PublicationVideo, UUID> {
    PublicationVideo findByVideoUrl(String videoUrl);

    Optional<PublicationVideo> findByPublication_Id(UUID publicationId);
}
