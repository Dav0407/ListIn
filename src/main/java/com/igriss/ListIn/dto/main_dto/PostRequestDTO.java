package com.igriss.ListIn.dto.main_dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID subcategoryId;
    //private List<AttributeDTO> attributes;
    private List<String> imageUrls;  // URLs for the images
}
