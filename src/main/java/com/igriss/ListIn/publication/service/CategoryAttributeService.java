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
    private final BrandModelService brandModelService;

    public List<GroupedAttributeDTO> getGroupedAttributesByCategoryId(UUID categoryId) {
        List<Object[]> results = repository.findAttributeKeysAndValuesByCategoryId(categoryId);

        Map<String, GroupedAttributeDTO> groupedAttributes = new LinkedHashMap<>();

        for (Object[] record : results) {

            AttributeKey attributeKey = AttributeKey.builder()
                    .id((UUID) record[0])
                    .name((String) record[1])
                    .helperText((String) record[2])
                    .subHelperText((String) record[3])
                    .widgetType((String) record[4])
                    .subWidgetType((String) record[5])
                    .filterText((String) record[6])
                    .subFilterText((String) record[7])
                    .filterWidgetType((String) record[8])
                    .subFilterWidgetType((String) record[9])
                    .dataType((String) record[10])
                    .build();

            AttributeValue attributeValue = AttributeValue.builder()
                    .id((UUID) record[11])
                    .value((String) record[12])
                    .build();

            groupedAttributes.computeIfAbsent(attributeKey.getName(), key -> GroupedAttributeDTO.builder()
                    .attributeKey(attributeKey.getName())
                    .helperText(attributeKey.getHelperText())
                    .subHelperText(attributeKey.getSubHelperText())
                    .widgetType(attributeKey.getWidgetType())
                    .subWidgetType(attributeKey.getSubWidgetType())
                    .filterHelperText(attributeKey.getFilterText())
                    .subFilterText(attributeKey.getSubFilterText())
                    .filterWidgetType(attributeKey.getFilterWidgetType())
                    .subFilterWidgetType(attributeKey.getSubFilterWidgetType())
                    .dataType(attributeKey.getDataType())
                    .values(new ArrayList<>())
                    .build()
            );

            if (attributeValue != null) {

                groupedAttributes.get(attributeKey.getName()).getValues().add(
                        new GroupedAttributeDTO.AttributeValueDTO(
                                attributeValue.getId().toString(),
                                attributeKey.getId().toString(),
                                attributeValue.getValue(),
                                brandModelService.getModels(attributeValue)
                        )
                );
            }
        }

        return new ArrayList<>(groupedAttributes.values());
    }
}
