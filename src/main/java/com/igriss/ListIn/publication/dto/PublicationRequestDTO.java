package com.igriss.ListIn.publication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
public class PublicationRequestDTO {

    private String title;

    private String description;

    private Float price;

    private Integer stockQuantity;

    private List<CategoryResponseDTO> categories;

    private String productCondition;

    private String publicationType;

    private String publicationStatus;

}
