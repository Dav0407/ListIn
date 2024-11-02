package com.igriss.ListIn.publication.dto;

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

    private Integer stockQuantity;

    private List<String> productImages;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CategoryResponseDTO> categories;

    private String productCondition;

    private String publicationType;

    private String publicationStatus;

    private UserResponseDTO seller;
}
