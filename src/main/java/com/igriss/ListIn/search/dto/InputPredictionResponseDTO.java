package com.igriss.ListIn.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InputPredictionResponseDTO implements Serializable {


    private UUID childAttributeValueId;

    private String childAttributeValue;

    private UUID parentAttributeValueId;

    private String parentAttributeValue;

    private UUID parentAttributeKeyId;

    private UUID childAttributeKeyId;

    private UUID parentCategoryId;

    private String parentCategoryName;

    private UUID categoryId;

    private String categoryName;

    private Long totalFound;

}
