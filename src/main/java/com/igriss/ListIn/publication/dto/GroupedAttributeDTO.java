package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupedAttributeDTO implements Serializable {
    private String attributeKey;
    private String helperText;
    private String subHelperText;
    private String widgetType;
    private String subWidgetType;
    private String filterHelperText;
    private String subFilterText;
    private String filterWidgetType;
    private String subFilterWidgetType;
    private String dataType;
    private List<AttributeValueDTO> values;

    @Data
    @AllArgsConstructor
    public static class AttributeValueDTO implements Serializable{
        private String attributeValueId;
        private String attributeKeyId;
        private String value;
        private List<BrandModelDTO> list;
    }
}