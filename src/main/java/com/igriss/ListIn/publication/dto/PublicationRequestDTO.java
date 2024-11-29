package com.igriss.ListIn.publication.dto;

import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
public class PublicationRequestDTO implements Serializable {

    private String title;

    private String description;

    private Float price;

    private Integer stockQuantity;

    private Boolean bargain;

    private String locationName;

    private String latitude;

    private String longitude;

    private List<String> imageUrls;

    private CategoryDTO categories;

    private String productCondition;

    private String publicationType;

    private String publicationStatus;

    private List<AttributeKey> attributeKeys;

    private List<AttributeValue> attributeValues;



}
