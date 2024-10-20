package com.igriss.ListIn.dto.main_dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubcategoryDTO {
    private UUID id;
    private String name;
    private String previewText;
    private String imagePath;
}