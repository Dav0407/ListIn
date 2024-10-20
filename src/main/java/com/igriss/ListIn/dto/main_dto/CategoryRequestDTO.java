package com.igriss.ListIn.dto.main_dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {
    private String name;
    private String previewText;
    private String imagePath; // Assuming images are stored as URLs or identifiers
}
