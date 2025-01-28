package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductVideoRepository extends JpaRepository<PublicationVideo, UUID> {
    PublicationVideo findByVideoUrl(String videoUrl);

    Optional<PublicationVideo> findByPublication_Id(UUID publicationId);

    void deleteByPublication_Id(UUID publicationId);

    Page<PublicationVideo> findAllByOrderByPublication_DateUpdatedDesc(Pageable pageable);
}
