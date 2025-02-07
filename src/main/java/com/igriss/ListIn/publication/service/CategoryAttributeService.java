package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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
                    .helperTextUz((String) record[15])
                    .subHelperTextUz((String) record[16])
                    .helperTextRu((String) record[17])
                    .subHelperTextRu((String) record[18])
                    .filterTextUz((String) record[19])
                    .subFilterTextUz((String) record[20])
                    .filterTextRu((String) record[21])
                    .subFilterTextRu((String) record[22])
                    .nameUz((String) record[23])
                    .nameRu((String) record[24])
                    .build();

            AttributeValue attributeValue = AttributeValue.builder()
                    .id((UUID) record[11])
                    .value((String) record[12])
                    .valueUz((String) record[13])
                    .valueRu((String) record[14])
                    .build();


            groupedAttributes.computeIfAbsent(attributeKey.getName(), key -> GroupedAttributeDTO.builder()
                    .attributeKey(attributeKey.getName())
                    .attributeKeyUz(attributeKey.getNameUz())
                    .attributeKeyRu(attributeKey.getNameRu())
                    .helperText(attributeKey.getHelperText())
                    .helperTextUz(attributeKey.getHelperTextUz())
                    .helperTextRu(attributeKey.getHelperTextRu())
                    .subHelperText(attributeKey.getSubHelperText())
                    .subHelperTextUz(attributeKey.getSubHelperTextUz())
                    .subHelperTextRu(attributeKey.getSubHelperTextRu())
                    .widgetType(attributeKey.getWidgetType())
                    .subWidgetType(attributeKey.getSubWidgetType())
                    .filterText(attributeKey.getFilterText())
                    .filterTextUz(attributeKey.getFilterTextUz())
                    .filterTextRu(attributeKey.getFilterTextRu())
                    .subFilterText(attributeKey.getSubFilterText())
                    .subFilterTextUz(attributeKey.getSubFilterTextUz())
                    .subFilterTextRu(attributeKey.getSubFilterTextRu())
                    .filterWidgetType(attributeKey.getFilterWidgetType())
                    .subFilterWidgetType(attributeKey.getSubFilterWidgetType())
                    .dataType(attributeKey.getDataType())
                    .values(new ArrayList<>())
                    .build()
            );

            if (attributeValue != null && attributeValue.getId() != null) {
                groupedAttributes.get(attributeKey.getName()).getValues().add(
                        new GroupedAttributeDTO.AttributeValueDTO(
                                attributeValue.getId().toString(),
                                attributeKey.getId().toString(),
                                attributeValue.getValue(),
                                attributeValue.getValueUz(),
                                attributeValue.getValueRu(),
                                brandModelService.getModels(attributeValue)
                        )
                );
            }
        }

        return new ArrayList<>(groupedAttributes.values());
    }
}
