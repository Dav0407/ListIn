package com.igriss.ListIn.publication.dto;

import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PublicationRequestDTO implements Serializable {

    private String title;

    private String description;

    private Float price;

    private Boolean bargain;

    private String locationName;

    private String latitude;

    private String longitude;

    private List<String> imageUrls;

    private CategoryDTO categories;

    private String productCondition;

    private String publicationStatus;

    private List<AttributeDTO> attributes;

    @Getter
    @Setter
    @Builder
    public static class AttributeDTO {
        @NotNull
        private AttributeKey attributeKey;

        @NotNull
        private AttributeValue attributeValue;
    }
}
