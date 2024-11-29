package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;
    private final ProductFileService productFileService;

    @Override
    //todo -> to write a more robust savePublication method with a completely working s3service and ProductImageService used
    public void savePublication(PublicationRequestDTO request, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        productFileService.saveImages(request.getImageUrls(), publication);

        List<AttributeKey> attributeKeys = request.getAttributeKeys();
        List<AttributeValue> attributeValues = request.getAttributeValues();

        for (AttributeKey attributeKey : attributeKeys) {
            CategoryAttribute.builder()
                    .category(publication.getCategory())
                    .attributeKey(attributeKey)
                    .build();
        }



    }
}
