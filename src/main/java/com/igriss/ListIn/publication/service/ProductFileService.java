package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductFileService {
    void saveImages(List<String> imageUrls, Publication publication);

    void saveVideo(String videoUrls, Publication publication);

    List<String> uploadImageURLs(List<MultipartFile> files);

    String uploadVideoURL(MultipartFile file);

    String findVideoUrlByPublicationId(UUID id);

    List<PublicationImage> findImagesByPublicationId(UUID id);

    void updateImagesByPublication(Publication publication, Map<Boolean, List<String>> imageUrls);

    void updateVideoByPublication(Publication publication, Map<Boolean, String> videoUrl);
}
