package com.igriss.ListIn.dto.main_dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubcategoryRequestDTO {
    private String name;
    private String previewText;
    private String imagePath; // Assuming images are stored as URLs or identifiers
    private UUID categoryId;  // Reference to the parent Category
}
