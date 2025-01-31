package com.igriss.ListIn.publication.dto;

import com.igriss.ListIn.publication.enums.ProductCondition;
import com.igriss.ListIn.publication.enums.PublicationType;
import com.igriss.ListIn.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
//todo -> add a jakarta validation to each field
public class PublicationResponseDTO implements Serializable {

    private UUID id;

    private String title;

    private String description;

    private Float price;

    private Boolean bargain;

    private Boolean isLiked;

    private String locationName;

    private Double latitude;

    private Double longitude;

    private List<ImageDTO> productImages;

    private String videoUrl;

    private PublicationType publicationType;

    private ProductCondition productCondition;

    private Long likes;

    private Long views;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CategoryDTO category;

    private UserResponseDTO seller;
}
