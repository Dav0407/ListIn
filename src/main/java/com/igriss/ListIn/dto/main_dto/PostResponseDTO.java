package com.igriss.ListIn.dto.main_dto;

import lombok.*;

import java.math.BigDecimal;
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
    private UUID categoryId;
}
