package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupedAttributeDTO {
    private String attributeKey;
    private String helperText;
    private String subHelperText;
    private String widgetType;
    private String subWidgetType;
    private String dataType;
    private List<AttributeValueDTO> values;

    @Data
    @AllArgsConstructor
    public static class AttributeValueDTO {
        private String attributeValueId;
        private String attributeKeyId;
        private String value;
        private List<BrandModelDTO> list;
    }
}
