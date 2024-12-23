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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService {

    private final PublicationImageMapper publicationImageMapper;
    private final PublicationVideoMapper publicationVideoMapper;
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final S3Service s3Service;

    @Override
    public void saveImages(List<String> imageUrls, Publication publication) {

        List<PublicationImage> publicationImageList = productImageRepository.findAllByImageUrlIn(imageUrls);
        publicationImageList.forEach(publicationImage -> publicationImage.setPublication(publication));

        productImageRepository.saveAll(publicationImageList);
    }

    @Override
    public void saveVideo(String videoUrls, Publication publication) {
        PublicationVideo publicationVideo = productVideoRepository.findByVideoUrl(videoUrls);
        publicationVideo.setPublication(publication);
        productVideoRepository.save(publicationVideo);
    }

    @Override
    public Map<String, String> saveFileURLs(List<MultipartFile> files, MultipartFile video) {
        Map<String, String> urls = s3Service.uploadFile(files, video);
        List<PublicationImage> publicationImages = new ArrayList<>();
        PublicationVideo publicationVideo;
        for (String k : urls.keySet())
            if (k.equals("image"))
                publicationImages.add(publicationImageMapper.toProductImage(urls.get(k)));
            else if (k.equals("video")){
                publicationVideo = publicationVideoMapper.toProductVideo(urls.get("video"));
                productVideoRepository.save(publicationVideo);
            }

        productImageRepository.saveAll(publicationImages);
        return urls;
    }
}
