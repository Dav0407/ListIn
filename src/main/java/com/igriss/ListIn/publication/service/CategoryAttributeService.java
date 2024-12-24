package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryAttributeService {

    private final CategoryAttributeRepository repository;
    private final BrandModelService brandModelService;

    public List<GroupedAttributeDTO> getGroupedAttributesByCategoryId(UUID categoryId) {
        List<Object[]> results = repository.findAttributeKeysAndValuesByCategoryId(categoryId);

        Map<String, GroupedAttributeDTO> groupedAttributes = results.parallelStream()
                .collect(Collectors.toMap(
                        record -> (String) record[1],
                        this::mapRecordToDTO,
                        this::mergeGroupedAttributes,
                        LinkedHashMap::new
                ));

        return new ArrayList<>(groupedAttributes.values());
    }

    private GroupedAttributeDTO mapRecordToDTO(Object[] record) {
        AttributeKey attributeKey = AttributeKey.builder()
                .id((UUID) record[0])
                .name((String) record[1])
                .helperText((String) record[2])
                .subHelperText((String) record[3])
                .widgetType((String) record[4])
                .subWidgetType((String) record[5])
                .dataType((String) record[6])
                .build();

        AttributeValue attributeValue = AttributeValue.builder()
                .id((UUID) record[7])
                .value((String) record[8])
                .build();

        GroupedAttributeDTO groupedAttributeDTO = new GroupedAttributeDTO(
                attributeKey.getName(),
                attributeKey.getHelperText(),
                attributeKey.getSubHelperText(),
                attributeKey.getWidgetType(),
                attributeKey.getSubWidgetType(),
                attributeKey.getDataType(),
                new ArrayList<>()
        );

        if (attributeValue != null) {
            groupedAttributeDTO.getValues().add(new GroupedAttributeDTO.AttributeValueDTO(
                    attributeValue.getId().toString(),
                    attributeKey.getId().toString(),
                    attributeValue.getValue(),
                    brandModelService.getCorrespondingModels(attributeKey, attributeValue)
            ));
        }

        return groupedAttributeDTO;
    }

    private GroupedAttributeDTO mergeGroupedAttributes(GroupedAttributeDTO dto1, GroupedAttributeDTO dto2) {
        dto1.getValues().addAll(dto2.getValues());
        return dto1;
    }
}
