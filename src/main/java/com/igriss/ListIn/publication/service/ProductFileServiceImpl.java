package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.config.Images.S3Service;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.mapper.PublicationVideoMapper;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import com.igriss.ListIn.publication.repository.ProductVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductFileServiceImpl implements ProductFileService {

    private final PublicationImageMapper publicationImageMapper;
    private final PublicationVideoMapper publicationVideoMapper;
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final S3Service s3Service;

    @Override
    public void saveImages(List<String> imageUrls, Publication publication) {

        List<PublicationImage> publicationImageList = imageUrls.stream()
                .map(url -> publicationImageMapper
                        .toProductImage(url, publication)).toList();

        List<PublicationImage> publicationImages = productImageRepository.saveAll(publicationImageList);
        log.info("Saved {} publication images: {}", publicationImages.size(), publicationImages);
    }

    @Override
    public void saveVideo(String videoUrl, Publication publication) {
        PublicationVideo publicationVideo = publicationVideoMapper.toProductVideo(videoUrl, publication);
        productVideoRepository.save(publicationVideo);
    }

    @Override
    public List<String> uploadImageURLs(List<MultipartFile> files) {
        return s3Service.uploadFile(files);
    }

    @Override
    public String uploadVideoURL(MultipartFile file) {
        return s3Service.uploadFile(List.of(file)).get(0);
    }

    @Override
    public List<PublicationImage> findImagesByPublicationId(UUID id) {
        return productImageRepository.findAllByPublication_Id(id);
    }

    @Override
    public String findVideoUrlByPublicationId(UUID id) {
        return productVideoRepository.findByPublication_Id(id).orElse(new PublicationVideo()).getVideoUrl();
    }

    @Override
    public void updateImagesByPublication(Publication publication, List<String> imageUrls) {
        productImageRepository.deleteAllByPublication_Id(publication.getId());
        saveImages(imageUrls,publication);
    }

    @Override
    public void updateVideoByPublication(Publication publication, String videoUrl) {
       productVideoRepository.deleteByPublication_Id(publication.getId());
       saveVideo(videoUrl,publication);
    }

}
