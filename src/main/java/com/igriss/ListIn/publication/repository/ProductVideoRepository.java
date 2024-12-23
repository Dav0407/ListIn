package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.PublicationVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVideoRepository extends JpaRepository<PublicationVideo, Long> {
    PublicationVideo findByVideoUrl(String videoUrl);

}
