package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.config.Images.S3Service;
import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.ProductImageMapper;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService{

    private final ProductImageMapper productImageMapper;
    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

    @Override
    public void saveImages(List<String> imageUrls, Publication publication) {

        List<ProductImage> productImageList = productImageRepository.findAllByImageUrlIn(imageUrls);
        productImageList.forEach(productImage -> productImage.setPublication(publication));

        productImageRepository.saveAll(productImageList);
    }

    public List<String> saveFileURLs(List<MultipartFile> files) {
        List<String> urls = s3Service.uploadFile(files);
        List<ProductImage> productImages = urls.stream().map(productImageMapper::toProductImage).toList();
        productImageRepository.saveAll(productImages);
        return urls;
    }
}
