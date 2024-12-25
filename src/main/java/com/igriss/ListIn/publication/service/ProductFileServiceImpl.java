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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<String> saveFileURLs(List<MultipartFile> files) {
        List<String> urls = s3Service.uploadFile(files);
        productImageRepository.saveAll(urls.stream().map(publicationImageMapper::toProductImage).toList());
        return urls;
    }

    @Override
    public String saveFileURLs(MultipartFile file) {
        List<String> url = s3Service.uploadFile(List.of(file));
        productVideoRepository.save(publicationVideoMapper.toProductVideo(url.get(0)));
        return url.get(0);
    }
}
