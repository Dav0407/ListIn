package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandModelDTO {
    private UUID modelId;
    private String name;
    private String attributeId;
}
