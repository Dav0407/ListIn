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
    private String attributeKeyUz;
    private String attributeKeyRu;
    private String helperText;
    private String subHelperText;
    private String helperTextUz;
    private String subHelperTextUz;
    private String helperTextRu;
    private String subHelperTextRu;
    private String widgetType;
    private String subWidgetType;
    private String filterText;
    private String subFilterText;
    private String filterTextUz;
    private String subFilterTextUz;
    private String filterTextRu;
    private String subFilterTextRu;
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
        private String valueUz;
        private String valueRu;
        private List<BrandModelDTO> list;
    }
}