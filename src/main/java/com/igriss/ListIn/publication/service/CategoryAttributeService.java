package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryAttributeService {

    private final CategoryAttributeRepository repository;

    public List<GroupedAttributeDTO> getGroupedAttributesByCategoryId(UUID categoryId) {
        List<Object[]> results = repository.findAttributeKeysAndValuesByCategoryId(categoryId);

        Map<String, GroupedAttributeDTO> groupedAttributes = new LinkedHashMap<>();

        for (Object[] record : results) {

            AttributeKey attributeKey = AttributeKey.builder()
                    .id((UUID) record[0])
                    .name((String) record[1])
                    .helperText((String) record[2])
                    .dataType((String) record[3])
                    .build();

            AttributeValue attributeValue = AttributeValue.builder()
                    .id((UUID) record[4])
                    .value((String) record[5])
                    .build();

            groupedAttributes.computeIfAbsent(attributeKey.getName(), key -> new GroupedAttributeDTO(
                    attributeKey.getName(),
                    attributeKey.getHelperText(),
                    attributeKey.getDataType(),
                    new ArrayList<>()
            ));

            if (attributeValue != null) {

                groupedAttributes.get(attributeKey.getName()).getValues().add(
                        new GroupedAttributeDTO.AttributeValueDTO(
                                attributeValue.getId().toString(),
                                attributeKey.getId().toString(),
                                attributeValue.getValue()
                        )
                );
            }
        }

        return new ArrayList<>(groupedAttributes.values());
    }
}
