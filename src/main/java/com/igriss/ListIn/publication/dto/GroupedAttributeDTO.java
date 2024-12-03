package com.igriss.ListIn.publication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupedAttributeDTO {
    private String attributeKey;
    private String helperText;
    private String dataType;
    private List<AttributeValueDTO> values;

    @Data
    @AllArgsConstructor
    public static class AttributeValueDTO {
        private String attributeValueId;
        private String attributeKeyId;
        private String value;
    }
}
