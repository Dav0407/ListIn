package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.PublicationAttributeValueDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PublicationAttributeValueMapper{

    private final PublicationAttributeValueRepository publicationAttributeValueRepository;

    public PublicationAttributeValueDTO toPublicationAttributeValueDTO(Publication publication) {
        return PublicationAttributeValueDTO.builder()
                .parentCategory(publication.getCategory().getParentCategory().getName())
                .category(publication.getCategory().getName())
                .attributes(toAttributes(publication.getId()))
                .build();
    }

    private Map<String, Map<String, List<String>>> toAttributes(UUID publicationId) {
        Map<String, Map<String, List<String>>> result = new HashMap<>();

        List<PublicationAttributeValue> publicationAttributeValues = publicationAttributeValueRepository.findByPublication_Id(publicationId);

        publicationAttributeValues.forEach(pav -> {
            String key = pav.getCategoryAttribute().getAttributeKey().getName();
            String keyUz = pav.getCategoryAttribute().getAttributeKey().getNameUz();
            String keyRu = pav.getCategoryAttribute().getAttributeKey().getNameRu();

            String value = pav.getAttributeValue().getValue();
            String valueUz = pav.getAttributeValue().getValueUz();
            String valueRu = pav.getAttributeValue().getValueRu();

            result.computeIfAbsent("en", k -> new HashMap<>()).computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            result.computeIfAbsent("uz", k -> new HashMap<>()).computeIfAbsent(keyUz, k -> new ArrayList<>()).add(valueUz);
            result.computeIfAbsent("ru", k -> new HashMap<>()).computeIfAbsent(keyRu, k -> new ArrayList<>()).add(valueRu);
        });

        return result;
    }
}