package com.igriss.ListIn.publication.dto;


import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationAttributeValueDTO {

    private String parentCategory;

    private String category;

    private Map<String, Map<String, List<String>>> attributes;

    private List<NumericValueResponseDTO> numericValues;
}
