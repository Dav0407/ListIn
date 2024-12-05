package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final PublicationMapper publicationMapper;
    private final ProductFileService productFileService;
    private final PublicationRepository publicationRepository;
    private final PublicationAttributeValueRepository publicationAttributeValueRepository;

    @Transactional
    @Override
    public void savePublication(PublicationRequestDTO request, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        productFileService.saveImages(request.getImageUrls(), publication);

        List<PublicationRequestDTO.AttributeDTO> attributes = request.getAttributes();

        saveAttributeKeysAndValues(attributes, publication);
    }

    private void saveAttributeKeysAndValues(List<PublicationRequestDTO.AttributeDTO> attributes, Publication publication) {

        List<PublicationAttributeValue> publicationAttributeValues = new ArrayList<>();

        for (PublicationRequestDTO.AttributeDTO attribute : attributes) {

            var categoryAttribute = CategoryAttribute.builder()
                    .category(publication.getCategory())
                    .attributeKey(attribute.getAttributeKey())
                    .build();

            var publicationAttributeValue = PublicationAttributeValue.builder()
                    .categoryAttribute(categoryAttribute)
                    .value(attribute.getAttributeValue().getValue())
                    .publication(publication)
                    .build();
            publicationAttributeValues.add(publicationAttributeValue);
        }
        publicationAttributeValueRepository.saveAll(publicationAttributeValues);
    }
}
