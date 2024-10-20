package com.igriss.ListIn.dto.main_dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private CategoryDTO category;
    private SubcategoryDTO subcategory;
    private List<AttributeDTO> attributes;
    private List<String> imageUrls;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
