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

    public PublicationAttributeValueDTO toPublicationAttributeValueDTO(Publication publication){
        return PublicationAttributeValueDTO.builder()
                .parentCategory(publication.getCategory().getParentCategory().getName())
                .category(publication.getCategory().getName())
                .attributes(toAttributes(publication.getId()))
                .build();
    }

    private Map<String, List<String>> toAttributes(UUID publicationId){
        Map<String, List<String>> result = new HashMap<>();

        List<PublicationAttributeValue> byPublicationId = publicationAttributeValueRepository.findByPublication_Id(publicationId);
        for (PublicationAttributeValue pav : byPublicationId){
            String key = pav.getCategoryAttribute().getAttributeKey().getName();
            String value = pav.getAttributeValue().getValue();
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

        }
        return result;
    }

}
