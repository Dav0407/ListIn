package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.*;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final PublicationMapper publicationMapper;
    private final ProductFileService productFileService;
    private final PublicationRepository publicationRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;
    private final PublicationAttributeValueRepository publicationAttributeValueRepository;

    @Override
    public void savePublication(PublicationRequestDTO request, Authentication authentication) {

        User connectedUser = (User) authentication.getPrincipal();

        Publication publication = publicationMapper.toPublication(request, connectedUser);
        publication = publicationRepository.save(publication);

        productFileService.saveImages(request.getImageUrls(), publication);

        List<AttributeKey> attributeKeys = request.getAttributeKeys();
        List<AttributeValue> attributeValues = request.getAttributeValues();

        saveAttributeKeysAndValues(attributeKeys, publication, attributeValues);
    }

    private void saveAttributeKeysAndValues(List<AttributeKey> attributeKeys, Publication publication, List<AttributeValue> attributeValues) {
        for (AttributeKey attributeKey : attributeKeys) {

            CategoryAttribute categoryAttribute = CategoryAttribute.builder()
                    .category(publication.getCategory())
                    .attributeKey(attributeKey)
                    .build();
            categoryAttribute = categoryAttributeRepository.save(categoryAttribute);

            String correspondingValue = attributeValues.stream()
                    .filter(attributeValue ->
                            attributeValue.getAttributeKey().getId()
                                    .equals(attributeKey.getId()))
                    .map(AttributeValue::getValue).findFirst()
                    .orElseThrow(()->new NoSuchElementException("No matching attribute value found"));

            PublicationAttributeValue publicationAttributeValue = PublicationAttributeValue.builder()
                    .categoryAttribute(categoryAttribute)
                    .value(correspondingValue)
                    .publication(publication)
                    .build();
            publicationAttributeValueRepository.save(publicationAttributeValue);
        }
    }
}
