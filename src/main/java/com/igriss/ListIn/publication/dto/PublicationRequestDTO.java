package com.igriss.ListIn.publication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PublicationRequestDTO implements Serializable {

    private String title;

    private String description;

    private Float price;

    private Boolean bargain;

    private String phoneNumber;

    private Boolean isGrantedForPreciseLocation;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime fromTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime toTime;

    private String locationName;

    private Double latitude;

    private Double longitude;

    private String productCondition;

    private List<String> imageUrls;

    private String videoUrl;

    private UUID categoryId;

    private List<AttributeValueDTO> attributeValues;

    @Getter
    @Setter
    @Builder
    public static class AttributeValueDTO {
        private UUID attributeId;
        private List<UUID> attributeValueIds;
    }
}
