package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.Publication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductFileService {
    void saveImages(List<String> imageUrls, Publication publication);
    List<String> saveFileURLs(List<MultipartFile> files);
}
