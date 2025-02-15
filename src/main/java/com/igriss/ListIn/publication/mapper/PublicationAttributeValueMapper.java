package com.igriss.ListIn.publication.mapper;


import com.igriss.ListIn.publication.dto.NumericValueResponseDTO;
import com.igriss.ListIn.publication.dto.PublicationAttributeValueDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationAttributeValueMapper{

    private final PublicationAttributeValueRepository publicationAttributeValueRepository;

    public PublicationAttributeValueDTO toPublicationAttributeValueDTO(Publication publication, List<NumericValue> numericValues) {
        return PublicationAttributeValueDTO.builder()
                .parentCategory(publication.getCategory().getParentCategory().getName())
                .category(publication.getCategory().getName())
                .attributes(toAttributes(publication.getId()))
                .numericValues(!(numericValues == null || numericValues.isEmpty()) ? toNumericValueDTO(numericValues) : null)
                .build();
    }

    private Map<String, Map<String, List<String>>> toAttributes(UUID publicationId) {
        Map<String, Map<String, List<String>>> result = new HashMap<>();

        List<PublicationAttributeValue> publicationAttributeValues = publicationAttributeValueRepository.findByPublication_Id(publicationId);

        publicationAttributeValues.forEach(pav -> {
            String key = pav.getCategoryAttribute().getAttributeKey().getFilterText();
            String keyUz = pav.getCategoryAttribute().getAttributeKey().getFilterTextUz();
            String keyRu = pav.getCategoryAttribute().getAttributeKey().getFilterTextRu();

            String value = pav.getAttributeValue().getValue();
            String valueUz = pav.getAttributeValue().getValueUz();
            String valueRu = pav.getAttributeValue().getValueRu();

            result.computeIfAbsent("en", k -> new HashMap<>()).computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            result.computeIfAbsent("uz", k -> new HashMap<>()).computeIfAbsent(keyUz, k -> new ArrayList<>()).add(valueUz);
            result.computeIfAbsent("ru", k -> new HashMap<>()).computeIfAbsent(keyRu, k -> new ArrayList<>()).add(valueRu);
        });

        return result;
    }

    private List<NumericValueResponseDTO> toNumericValueDTO(List<NumericValue> numericValues) {
        return numericValues.stream().map(numericValue -> NumericValueResponseDTO.builder()
                .numericField(numericValue.getNumericField().getFieldName())
                .numericFieldUz(numericValue.getNumericField().getFieldNameUz())
                .numericFieldRu(numericValue.getNumericField().getFieldNameRu())
                .numericValue(numericValue.getValue())
                .build()
        ).toList();
    }

}