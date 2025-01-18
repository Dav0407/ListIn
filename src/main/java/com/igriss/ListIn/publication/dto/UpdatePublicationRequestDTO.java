package com.igriss.ListIn.publication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UpdatePublicationRequestDTO {

    private String title;

    private String description;

    private Float price;

    private Boolean bargain;

    private String productCondition;

    private List<String> imageUrls;

    private String videoUrl;


    private List<UpdatePublicationRequestDTO.AttributeValueDTO> attributeValues;

    @Getter
    @Setter
    @Builder
    public static class AttributeValueDTO {
        private UUID attributeId;
        private List<UUID> attributeValueIds;
    }
}
