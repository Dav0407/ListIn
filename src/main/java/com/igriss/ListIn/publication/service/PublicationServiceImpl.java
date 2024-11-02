package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.config.Images.S3Service;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.ProductImage;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.ProductConditionRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final S3Service service;
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;
    private final ProductImageService productImageService;

    @Override
    //todo -> to write a more robust savePublication method with a completely working s3service and ProductImageService used
    public void savePublication(PublicationRequestDTO request, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        List<String> imageUrls = request.getImageUrls();
        Publication publication = publicationMapper.toPublication(request, connectedUser, imageUrls);
        List<ProductImage> images = publication.getImages();
        productImageService.saveImages(images);

        publicationRepository.save(publication);
    }
}
