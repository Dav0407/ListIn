package com.igriss.ListIn.publication.dto.user_publications;

import com.igriss.ListIn.publication.dto.CategoryDTO;
import com.igriss.ListIn.publication.dto.ImageDTO;
import com.igriss.ListIn.publication.dto.NumericValueResponseDTO;
import com.igriss.ListIn.publication.dto.PublicationAttributeValueDTO;
import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
//todo -> add a jakarta validation to each field
public class UserPublicationDTO {

    private UUID id;

    private String title;

    private String description;

    private Float price;

    private Boolean bargain;

    private String locationName;

    private Double latitude;

    private Double longitude;

    private List<ImageDTO> productImages;

    private String videoUrl;

    private PublicationType publicationType;

    private ProductCondition productCondition;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CategoryDTO category;

    private PublicationAttributeValueDTO attributeValue;

    private List<NumericValueResponseDTO> numericValues;
}
