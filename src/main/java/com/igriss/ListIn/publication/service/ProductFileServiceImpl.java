package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.config.Images.S3Service;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService{

    private final PublicationImageMapper publicationImageMapper;
    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

    @Override
    public void saveImages(List<String> imageUrls, Publication publication) {

        List<PublicationImage> publicationImageList = productImageRepository.findAllByImageUrlIn(imageUrls);
        publicationImageList.forEach(publicationImage -> publicationImage.setPublication(publication));

        productImageRepository.saveAll(publicationImageList);
    }

    public List<String> saveFileURLs(List<MultipartFile> files) {
        List<String> urls = s3Service.uploadFile(files);
        List<PublicationImage> publicationImages = urls.stream().map(publicationImageMapper::toProductImage).toList();
        productImageRepository.saveAll(publicationImages);
        return urls;
    }
}
